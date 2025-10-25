package com.flink.adapter.operator;

import org.apache.flink.api.common.functions.ReduceFunction;

public class AverageSessionAggregate implements ReduceFunction<DepartmentAverage> {

    @Override
    public DepartmentAverage reduce(DepartmentAverage d1, DepartmentAverage d2) {
        long avg = (d1.getAvgSessionTime() + d2.getAvgSessionTime()) / 2;
        return new DepartmentAverage(d1.getDepartmentId(), avg);
    }
}
