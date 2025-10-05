package com.flink.adapter;

import com.flink.adapter.source.UserSession;
import com.flink.adapter.source.UserSessionSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamJob {
    public static void main(String[] args) throws Exception {

        // Set up environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Add source
        DataStream<UserSession>  userSessionDataStream = env.addSource(new UserSessionSource())
                .name("UserSessionSource")
                .name("PrintSink");

        userSessionDataStream.print().setParallelism(1);

        // Execute
        env.execute("Random User Session Generator");
    }
}
