package com.coco.ratelimit

import kotlin.random.Random

class CountMinSketch(
    private val depth: Int,
    private val width: Int,
) {

    private val sketch: Array<IntArray> = Array(depth) { IntArray(width) }
    private val hashSeeds: IntArray = IntArray(depth).apply {
        val random = Random
        for (index in 0 until depth) {
            this[index] = random.nextInt()
        }
    }

    fun add(item: String) {
        for ((index, seed) in hashSeeds.withIndex()) {
            val hash = hash(item, seed)
            sketch[index][hash % width] += 1
        }
    }

    fun estimateCount(item: String): Int {
        var min = Integer.MIN_VALUE
        for ((index, seed) in hashSeeds.withIndex()) {
            val hash = hash(item, seed)
            min = min.coerceAtLeast(sketch[index][hash % width])
        }
        return min
    }

    private fun hash(item: String, seed: Int): Int {
        return item.hashCode() * seed
    }

    fun reset() {
        sketch.forEach { it.fill(0) }
    }

}