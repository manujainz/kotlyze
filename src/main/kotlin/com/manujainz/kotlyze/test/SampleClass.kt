package com.manujainz.kotlyze.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleClass {

    private val sampleList = listOf("apple", "banana", "cherry")

    fun demonstrateIf() {
        val number = 5
        if (number % 2 == 0) {
            println("Even")
        } else {
            println("Odd")
        }

    }

    fun sampleFunction() {
        val condition1 = true
        val condition2 = false
        val condition3 = true

        if (condition1 && condition2 || condition3) {
            // ...
        }
    }


    fun demonstrateForLoop() {
        for (fruit in sampleList) {
            println(fruit)
        }
    }

    fun demonstrateWhen(number: Int): String {
        return when {
            number < 0 -> "Negative"
            number == 0 -> "Zero"
            else -> "Positive"
        }
    }

    fun demonstrateCoroutines() {
        CoroutineScope(Dispatchers.IO).launch {
            // Simulate some IO work
            Thread.sleep(1000)
            println("Coroutine finished")
        }
    }

    fun demonstrateLambda() {
        val result = sampleList.map { it.length }.filter { it > 4 }
        println(result)
    }
}
