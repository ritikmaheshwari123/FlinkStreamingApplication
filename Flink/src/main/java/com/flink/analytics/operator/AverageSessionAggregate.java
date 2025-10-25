package com.flink.analytics.operator;

import com.flink.analytics.model.DepartmentAverage;
import com.flink.analytics.model.EnrichedSession;
import org.apache.flink.api.common.functions.ReduceFunction;

public class AverageSessionAggregate implements ReduceFunction<EnrichedSession> {
    @Override
    public EnrichedSession reduce(EnrichedSession s1, EnrichedSession s2) {
        long avg = (s1.getSessionDuration() + s2.getSessionDuration()) / 2;
        return new EnrichedSession(
                new com.flink.analytics.model.UserSession(
                        s1.getUserId(), s1.getSessionId(),
                        s1.getSessionStartTime(), avg, s1.getDepartmentId()),
                s1.getEngagementLevel());
    }
}
