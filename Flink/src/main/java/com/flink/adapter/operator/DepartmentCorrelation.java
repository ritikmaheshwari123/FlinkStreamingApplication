package com.flink.adapter.operator;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

public class DepartmentCorrelation extends KeyedProcessFunction<Integer, DepartmentAverage, String> {

    private final Map<Integer, Long> latestAverages = new HashMap<>();

    @Override
    public void processElement(DepartmentAverage value, Context ctx, Collector<String> out) {
        latestAverages.put(value.getDepartmentId(), value.getAvgSessionTime());

        if (latestAverages.size() > 1) {
            for (Map.Entry<Integer, Long> e : latestAverages.entrySet()) {
                if (e.getKey() != value.getDepartmentId()) {
                    long diff = Math.abs(e.getValue() - value.getAvgSessionTime());
                    out.collect("Correlation: Dept " + value.getDepartmentId() +
                            " vs Dept " + e.getKey() +
                            " | Avg Session Time Diff = " + diff + " ms");
                }
            }
        }
    }
}
