package com.flink.adapter.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class UserSessionSource implements SourceFunction<UserSession> {
    private volatile boolean running = true;
    private final Random random = new Random();
    private long sessionCounter = 1L; // 👈 replace AtomicLong

    @Override
    public void run(SourceContext<UserSession> ctx) throws Exception {
        while (running) {
            int userId = random.nextInt(10) + 1;
            long sessionId = sessionCounter++; // simple increment
            long loggingTime = System.currentTimeMillis();
            long sessionTime = 1000 + random.nextInt(9000);
            int departmentId = random.nextInt(5) + 1;

            UserSession session = new UserSession(userId, sessionId, loggingTime, sessionTime, departmentId);
            ctx.collect(session);

            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}