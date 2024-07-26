package com.example.on_boarding

import org.junit.Test

import org.junit.Assert.*
import java.util.LinkedList
import java.util.Stack

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

    }

    @Test
    fun test() {
        val s1 = "aBcDeFg"
        var newS2 = ""
        for (i in s1.indices) {
            if (s1[i].isUpperCase()) {
                newS2 += s1[i].lowercase()
            } else {
                newS2 += s1[i].uppercase()
            }
        }
        print(newS2)

    }

    @Test
    fun test2() {
        // mutable객체는 수정이 가능하다.
        var intSet: Set<Int> = mutableSetOf(1, 5, 5)
        intSet += 9
        intSet += 6
        intSet += 7
        intSet += 8

        println(intSet.sorted()) // [1, 5, 4, 2]

    }

    @Test
    fun test5() {
        var q = LinkedList<Int>()
        q.add(1)
        q.add(2)
        q.add(3)
        q.add(4)
        q.add(5)
        var s = Stack<Int>()
        s.push(1)
        s.push(2)
        s.push(3)
        s.push(4)


    }

    @Test
    fun test3() {
        val players = arrayOf("mumu", "soe", "poe", "kai", "mine")
        val callings = arrayOf("kai", "kai", "mine", "mine")
        val playersMap = linkedMapOf<String, Int>()
        players.forEachIndexed { index, s -> playersMap[s] = index }

        callings.forEach { call ->
            val index = playersMap[call] ?: 0
            val prePlayer = players[index-1]
            players[index] = players[index - 1]
            players[index - 1] = call
            playersMap[call] = playersMap[call]!!-1
            playersMap[prePlayer] = playersMap[prePlayer]!!+1

        }
        println(players.contentToString())

    }


}