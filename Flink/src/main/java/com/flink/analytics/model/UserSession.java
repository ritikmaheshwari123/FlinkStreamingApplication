package com.flink.analytics.model;

public class UserSession {
    private int userId;
    private long sessionId;
    private long sessionStartTime;
    private long sessionDuration;
    private int departmentId;

    public UserSession() {}

    public UserSession(int userId, long sessionId, long sessionStartTime, long sessionDuration, int departmentId) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.sessionStartTime = sessionStartTime;
        this.sessionDuration = sessionDuration;
        this.departmentId = departmentId;
    }

    public int getUserId() { return userId; }
    public long getSessionId() { return sessionId; }
    public long getSessionStartTime() { return sessionStartTime; }
    public long getSessionDuration() { return sessionDuration; }
    public int getDepartmentId() { return departmentId; }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId=" + userId +
                ", sessionId=" + sessionId +
                ", duration=" + sessionDuration +
                ", departmentId=" + departmentId +
                '}';
    }
}
