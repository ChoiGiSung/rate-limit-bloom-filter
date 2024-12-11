package com.coco.ratelimit

import java.util.concurrent.Executors

class WindowManager(
    private val rateLimiter: RateLimiter,
    private val windowDuration: Long
) {

    fun startWindowRotation() {
        val scheduler = Executors.newScheduledThreadPool(1)

        scheduler.scheduleAtFixedRate(
            {
                rateLimiter.reset()
            },
            windowDuration,
            windowDuration,
            java.util.concurrent.TimeUnit.SECONDS
        )
    }
}