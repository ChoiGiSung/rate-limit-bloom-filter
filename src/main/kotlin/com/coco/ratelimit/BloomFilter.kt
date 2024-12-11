package com.coco.ratelimit

import java.util.*
import kotlin.math.abs

class BloomFilter(
    private val size: Int,
    private val hashSeeds: IntArray
) {
    private val bitSet: BitSet = BitSet(size)

    fun add(item: String) {
        for (seed in hashSeeds) {
            val hash = hash(item, seed)
            bitSet.set(abs(hash % size))
        }
    }

    fun mightContain(item: String): Boolean {
        for (seed in hashSeeds) {
            val hash = hash(item, seed)
            if (!bitSet.get(abs(hash % size))) {
                return false
            }
        }
        return true
    }

    private fun hash(item: String, seed: Int): Int {
        return item.hashCode() * seed
    }

    fun reset() {
        bitSet.clear()
    }
}