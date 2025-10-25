package com.flink.analytics.operator;

import com.flink.analytics.model.DepartmentAverage;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class AnomalyDetectionProcess extends KeyedProcessFunction<Integer, DepartmentAverage, String> {

    private static final long THRESHOLD = 8000L; // Example threshold

    @Override
    public void processElement(DepartmentAverage value, Context ctx, Collector<String> out) {
        if (value.getAvgSessionDuration() > THRESHOLD) {
            out.collect("⚠️ High session time detected in department "
                    + value.getDepartmentId()
                    + ": " + value.getAvgSessionDuration() + " ms");
        }
    }
}
