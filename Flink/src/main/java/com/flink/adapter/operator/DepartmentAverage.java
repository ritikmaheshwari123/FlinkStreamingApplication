package com.flink.adapter.operator;

public class DepartmentAverage {
    private int departmentId;
    private long avgSessionTime;

    public DepartmentAverage() {}

    public DepartmentAverage(int departmentId, long avgSessionTime) {
        this.departmentId = departmentId;
        this.avgSessionTime = avgSessionTime;
    }

    public int getDepartmentId() { return departmentId; }
    public long getAvgSessionTime() { return avgSessionTime; }

    @Override
    public String toString() {
        return "DepartmentAverage{" +
                "departmentId=" + departmentId +
                ", avgSessionTime=" + avgSessionTime +
                '}';
    }
}
