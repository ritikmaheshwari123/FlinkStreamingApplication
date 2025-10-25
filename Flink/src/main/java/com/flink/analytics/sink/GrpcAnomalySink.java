package com.flink.analytics.sink;

import analytics.AnalyticsServiceGrpc;
import analytics.AnomalyRequest;
import analytics.AnomalyResponse;
import com.flink.analytics.model.DepartmentAverage;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

public class GrpcAnomalySink extends RichSinkFunction<String> {

    private transient ManagedChannel channel;
    private transient AnalyticsServiceGrpc.AnalyticsServiceBlockingStub stub;
    private final String host;
    private final int port;

    public GrpcAnomalySink(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void open(org.apache.flink.configuration.Configuration parameters) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        stub = AnalyticsServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void invoke(String value, Context context) {
        // Parse department/message from value string if needed
        String[] parts = value.split(": ", 2);
        String dept = parts[0];
        String msg = parts.length > 1 ? parts[1] : value;

        AnomalyRequest request = AnomalyRequest.newBuilder()
                .setDepartment(dept)
                .setMessage(msg)
                .build();

        AnomalyResponse response = stub.sendAnomaly(request);
        if (!response.getSuccess()) {
            System.err.println("Failed to send anomaly via gRPC: " + value);
        }
    }

    @Override
    public void close() {
        if (channel != null) {
            channel.shutdown();
        }
    }
}
