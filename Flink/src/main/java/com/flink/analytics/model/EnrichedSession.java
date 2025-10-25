package com.flink.analytics.model;

public class EnrichedSession extends UserSession {
    private String engagementLevel;

    public EnrichedSession() {}

    public EnrichedSession(UserSession session, String engagementLevel) {
        super(session.getUserId(), session.getSessionId(), session.getSessionStartTime(),
                session.getSessionDuration(), session.getDepartmentId());
        this.engagementLevel = engagementLevel;
    }

    public String getEngagementLevel() { return engagementLevel; }

    @Override
    public String toString() {
        return super.toString() + ", engagementLevel='" + engagementLevel + '\'';
    }
}
