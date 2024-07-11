package com.example.marvelapp.util

import com.example.marvelapp.domain.util.ResultState
import junit.framework.TestCase.assertEquals
import kotlin.test.assertEquals

fun <T> assertResultStateEquals(expected: ResultState<T>, actual: ResultState<T>) {
    when {
        expected is ResultState.Success && actual is ResultState.Success -> {
            assertEquals(expected.data, actual.data)
        }
        expected is ResultState.Error && actual is ResultState.Error -> {
            assertEquals(expected.exception::class, actual.exception::class)
            assertEquals(expected.exception.message, actual.exception.message)
        }
        expected is ResultState.Loading && actual is ResultState.Loading -> {
            // Both are loading, considered equal
        }
        else -> throw AssertionError("Expected and actual ResultState do not match: expected $expected but was $actual")
    }
}

fun <T> assertResultStateListEquals(expected: List<ResultState<T>>, actual: List<ResultState<T>>) {
    assertEquals(expected.size, actual.size, "Lists do not have the same size")
    for (i in expected.indices) {
        assertResultStateEquals(expected[i], actual[i])
    }
}
