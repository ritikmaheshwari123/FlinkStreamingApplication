package com.flink.adapter.source;

import java.time.Instant;

public class UserSession {
    private int userId;
    private long sessionId;
    private long loggingTime;
    private long sessionTime;
    private int departmentId;

    // Required for Flink serialization
    public UserSession() {}

    public UserSession(int userId, long sessionId, long loggingTime, long sessionTime, int departmentId) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.loggingTime = loggingTime;
        this.sessionTime = sessionTime;
        this.departmentId = departmentId;
    }

    public int getUserId() {
        return userId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getLoggingTime() {
        return loggingTime;
    }

    public long getSessionTime() {
        return sessionTime;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId=" + userId +
                ", sessionId=" + sessionId +
                ", loggingTime=" + Instant.ofEpochMilli(loggingTime) +
                ", sessionTime=" + sessionTime +
                ", departmentId=" + departmentId +
                '}';
    }
}
