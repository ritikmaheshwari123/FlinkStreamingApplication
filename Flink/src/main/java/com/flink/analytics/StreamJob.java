package com.flink.analytics;

import com.flink.analytics.model.DepartmentAverage;
import com.flink.analytics.model.EnrichedSession;
import com.flink.analytics.operator.*;
import com.flink.analytics.model.UserSession;
import com.flink.analytics.dataGenerator.UserSessionSource;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamJob {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<UserSession> userSessionStream = env
                .addSource(new UserSessionSource())
                .name("UserSessionSource")
                .assignTimestampsAndWatermarks(WatermarkStrategy.noWatermarks());

        userSessionStream.print("🎯 User Sessions");

        // ✅ 1. Kafka Source
//        KafkaSource<UserSession> kafkaSource = KafkaSource.<UserSession>builder()
//                .setBootstrapServers("localhost:9092")
//                .setTopics("user_sessions")
//                .setGroupId("flink-analytics-group")
//                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(UserSession.class))
//                .build();
//
//        DataStream<UserSession> inputStream = env.fromSource(
//                kafkaSource,
//                WatermarkStrategy.noWatermarks(),
//                "Kafka User Sessions Source");


        // ✅ 2. Filter invalid / short sessions
        DataStream<UserSession> filtered = userSessionStream
                .filter(new FilterInvalidSessions())
                .name("FilterInvalidSessions");

        // ✅ 3. Enrich with engagement category
        DataStream<EnrichedSession> enriched = filtered
                .map(new EnrichSession())
                .name("EnrichSession");

        // ✅ 4. Compute average session time by department
        DataStream<EnrichedSession> avgEnriched = enriched
                .keyBy(EnrichedSession::getDepartmentId)
                .reduce(new AverageSessionAggregate())
                .name("AverageSessionAggregate");

        DataStream<DepartmentAverage> avgPerDept = avgEnriched
                .map(e -> new DepartmentAverage(e.getDepartmentId(), e.getSessionDuration()))
                .returns(DepartmentAverage.class)
                .name("MapToDepartmentAverage");

        // ✅ 5. Detect anomalies in average session time
        DataStream<String> anomalies = avgPerDept
                .keyBy(DepartmentAverage::getDepartmentId)
                .process(new AnomalyDetectionProcess())
                .name("AnomalyDetection");


        anomalies.print("User Insights:::");

        // ✅ 6. Kafka Sink
//        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
//                .setBootstrapServers("localhost:9092")
//                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
//                        .setTopic("user_insights")
//                        .setValueSerializationSchema(msg -> msg.getBytes())
//                        .build())
//                .build();
//
//        anomalies.sinkTo(kafkaSink).name("KafkaSink");

        env.execute("Real-Time User Analytics Pipeline");

    }
}
