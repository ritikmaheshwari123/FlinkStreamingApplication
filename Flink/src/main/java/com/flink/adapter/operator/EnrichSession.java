package com.flink.adapter.operator;

import com.flink.adapter.source.UserSession;
import org.apache.flink.api.common.functions.MapFunction;

public class EnrichSession implements MapFunction<UserSession, EnrichedSession> {
    @Override
    public EnrichedSession map(UserSession session) {
        String category;
        if (session.getSessionTime() < 4000) category = "SHORT";
        else if (session.getSessionTime() < 7000) category = "MEDIUM";
        else category = "LONG";

        return new EnrichedSession(
                session.getUserId(),
                session.getSessionId(),
                session.getLoggingTime(),
                session.getSessionTime(),
                session.getDepartmentId(),
                category
        );
    }
}
