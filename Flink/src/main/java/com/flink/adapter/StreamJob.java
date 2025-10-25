package com.flink.adapter;

import com.flink.adapter.operator.*;
import com.flink.adapter.source.UserSession;
import com.flink.adapter.source.UserSessionSource;
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

        // Step 1: Filter out short sessions
        DataStream<UserSession> filtered = userSessionStream
                .filter(new FilterShortSessions())
                .name("FilterShortSessions");

        // Step 2: Transform (enrich)
        DataStream<EnrichedSession> enriched = filtered
                .map(new EnrichSession())
                .name("EnrichSession");

        // Step 3: Aggregate average session time by department
        DataStream<DepartmentAverage> averages = enriched
                .map(session -> new DepartmentAverage(
                        session.getDepartmentId(),
                        session.getSessionTime()))
                .returns(DepartmentAverage.class)
                .keyBy(DepartmentAverage::getDepartmentId)
                .reduce(new AverageSessionAggregate())
                .name("AverageSessionAggregate");


        // Step 4: Correlation between departments
        DataStream<String> correlation = averages
                .keyBy(DepartmentAverage::getDepartmentId)
                .process(new DepartmentCorrelation())
                .name("DepartmentCorrelation");

        // Step 5: Print final result
        correlation.print("📊 Department Correlation Results");

        env.execute("User Session Analytics Pipeline");
    }
}
