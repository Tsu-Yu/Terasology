package org.terasology.engine.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeStubTest {

    /**
     * THE STUB A custom stub implementing the Terasology Time interface. Stub the 'getGameTimeInMs()' method to return an exact value,
     * overriding the unpredictable real-world clock.
     */
    static class FixedTimeStub implements Time {
        private long fixedTimeMs;
        private float dilation = 1.0f;

        public FixedTimeStub(long timeMs) {
            this.fixedTimeMs = timeMs;
        }

        @Override
        public long getGameTimeInMs() {
            return fixedTimeMs;
        }

        @Override
        public float getGameTime() {
            return fixedTimeMs / 1000.0f;
        }

        @Override
        public float getRealDelta() {
            return 0;
        }

        @Override
        public long getRealDeltaInMs() {
            return 0;
        }

        @Override
        public long getRealTimeInMs() {
            return fixedTimeMs;
        }

        @Override
        public boolean isPaused() {
            return false;
        }

        @Override
        public void setPaused(boolean paused) {
        }

        @Override
        public float getDelta() {
            return 0.0f;
        }

        @Override
        public long getDeltaInMs() {
            return 0;
        }

        @Override
        public float getFps() {
            return 60.0f;
        }

        @Override public float getGameTimeDilation() {
            return dilation;
        }
        @Override public void setGameTimeDilation(float dilation) {
            this.dilation = dilation;
        }

        @Override
        public float getGameDelta() {
            return 0f;
        }

        @Override
        public long getGameDeltaInMs() {
            return 0L;
        }
    }

    /**
     * THE TEST
     * Check if a cooldown has expired and
     * use stubbed method instead of the real engine time.
     */
    @Test
    @DisplayName("Testable Design: Verify cooldown logic using the fixed-time stub")
    public void testCooldownWithStubbedTime() {
        // Setup: Control the environment by injecting a stub locked exactly at 5000ms
        Time timeStub = new FixedTimeStub(5000L);

        long lastActionTime = 4500L;
        long cooldownRequired = 1000L;

        // Execution: Calculate elapsed time using the stubbed method
        long timeElapsed = timeStub.getGameTimeInMs() - lastActionTime;
        boolean canPerformAction = timeElapsed >= cooldownRequired;

        // Verification: 5000 - 4500 = 500ms elapsed.
        // 500ms is less than the 1000ms cooldown, so the action is blocked.
        assertFalse(canPerformAction, "Action should be blocked by cooldown logic");
    }
}