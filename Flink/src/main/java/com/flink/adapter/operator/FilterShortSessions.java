package com.flink.adapter.operator;

import com.flink.adapter.source.UserSession;
import org.apache.flink.api.common.functions.FilterFunction;

public class FilterShortSessions implements FilterFunction<UserSession> {
    @Override
    public boolean filter(UserSession session) {
        return session.getSessionTime() >= 2000;
    }
}
