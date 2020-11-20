package com.example.weather.storage

import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.Test

class StorageTest {

    @Test
    fun isTimeWithinDay_zeroInput_false() {
        //assemble
        val storage = Storage()
        val input = 0L
        val expected = false

        //act
        val actual = storage.isTimeWithinDay(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun isTimeWithinDay_minusInput_false() {
        //assemble
        val storage = Storage()
        val input = -10L
        val expected = false

        //act
        val actual = storage.isTimeWithinDay(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun isTimeWithinDay_largeInput_false() {
        //assemble
        val storage = Storage()
        val input = 2000000000000L
        val expected = false

        //act
        val actual = storage.isTimeWithinDay(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun isTimeWithinDay_validInput_false() {
        //assemble
        val storage = Storage()
        val oneHourInMillis = 3600000
        val input = System.currentTimeMillis() - oneHourInMillis
        val expected = true

        //act
        val actual = storage.isTimeWithinDay(input)

        //assert
        assertEquals(expected, actual)
    }
}