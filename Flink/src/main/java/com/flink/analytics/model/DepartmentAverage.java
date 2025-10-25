package com.flink.analytics.model;

public class DepartmentAverage {
    private int departmentId;
    private long avgSessionDuration;

    public DepartmentAverage() {}

    public DepartmentAverage(int departmentId, long avgSessionDuration) {
        this.departmentId = departmentId;
        this.avgSessionDuration = avgSessionDuration;
    }

    public int getDepartmentId() { return departmentId; }
    public long getAvgSessionDuration() { return avgSessionDuration; }

    @Override
    public String toString() {
        return "DepartmentAverage{" +
                "departmentId=" + departmentId +
                ", avgSessionDuration=" + avgSessionDuration +
                '}';
    }
}
