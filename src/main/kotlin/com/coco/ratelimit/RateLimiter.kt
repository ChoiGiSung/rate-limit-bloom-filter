package com.coco.ratelimit

class RateLimiter(
    private val bloomFilterSize: Int,
    private val hashSeeds: IntArray,
    private val depth: Int,
    private val width: Int,
    private val maxRequests: Int
) {

    private val bloomFilter: BloomFilter = BloomFilter(
        bloomFilterSize,
        hashSeeds
    )
    private val countMinSketch: CountMinSketch = CountMinSketch(
        depth,
        width
    )

    fun isRequestAllowed(item: String): Boolean {
        if (bloomFilter.mightContain(item)) {
            val currentCount = countMinSketch.estimateCount(item)
            if (currentCount >= maxRequests) {
                return false
            }
        }

        bloomFilter.add(item)
        countMinSketch.add(item)
        return true
    }

    fun reset() {
        bloomFilter.reset()
        countMinSketch.reset()
    }

}