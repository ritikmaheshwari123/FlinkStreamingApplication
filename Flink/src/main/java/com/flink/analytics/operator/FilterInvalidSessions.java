package com.flink.analytics.operator;

import com.flink.analytics.model.UserSession;
import org.apache.flink.api.common.functions.FilterFunction;

public class FilterInvalidSessions implements FilterFunction<UserSession> {
    @Override
    public boolean filter(UserSession session) {
        // filter out invalid or too short sessions
        return session.getSessionDuration() >= 2000 && session.getUserId() > 0;
    }
}
