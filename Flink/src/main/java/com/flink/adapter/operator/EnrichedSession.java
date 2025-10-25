package com.flink.adapter.operator;

public class EnrichedSession {
    private int userId;
    private long sessionId;
    private long loggingTime;
    private long sessionTime;
    private int departmentId;
    private String sessionCategory;

    public EnrichedSession() {}

    public EnrichedSession(int userId, long sessionId, long loggingTime,
                           long sessionTime, int departmentId, String sessionCategory) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.loggingTime = loggingTime;
        this.sessionTime = sessionTime;
        this.departmentId = departmentId;
        this.sessionCategory = sessionCategory;
    }

    public int getUserId() { return userId; }
    public long getSessionId() { return sessionId; }
    public long getLoggingTime() { return loggingTime; }
    public long getSessionTime() { return sessionTime; }
    public int getDepartmentId() { return departmentId; }
    public String getSessionCategory() { return sessionCategory; }

    @Override
    public String toString() {
        return "EnrichedSession{" +
                "userId=" + userId +
                ", sessionId=" + sessionId +
                ", departmentId=" + departmentId +
                ", sessionCategory='" + sessionCategory + '\'' +
                '}';
    }
}
