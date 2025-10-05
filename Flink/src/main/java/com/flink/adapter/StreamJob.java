package com.flink.adapter;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStream<String> text = env.fromElements(
                "Apache Flink is powerful",
                "Flink supports both batch and streaming",
                "Flink is fun"
        );

        // Step 3: Transform the data
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordCounts = text
                .flatMap(new Tokenizer()) // Split sentences into words
                .keyBy(value -> value.f0) // Group by word
                .sum(1); // Sum the counts

        // Step 4: Print the result
        wordCounts.print();

        // Step 5: Execute the program
        env.execute("Flink Basic Streaming Example");
    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // Split by spaces
            String[] tokens = value.toLowerCase().split("\\s+");
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
