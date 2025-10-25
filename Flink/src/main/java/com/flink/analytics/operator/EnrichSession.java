package com.flink.analytics.operator;

import com.flink.analytics.model.EnrichedSession;
import com.flink.analytics.model.UserSession;
import org.apache.flink.api.common.functions.MapFunction;

public class EnrichSession implements MapFunction<UserSession, EnrichedSession> {
    @Override
    public EnrichedSession map(UserSession session) {
        String level;
        long duration = session.getSessionDuration();
        if (duration < 4000) level = "LOW";
        else if (duration < 7000) level = "MEDIUM";
        else level = "HIGH";

        return new EnrichedSession(session, level);
    }
}
