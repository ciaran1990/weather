package com.example.weather.utilities

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class UtilitiesTest {


    @Test
    fun formatTemperature_validEmptyInput_correctOutput(){
        //assemble
        val utilities = Utilities()
        val input = 0.0
        val expected = "-273°C"

        //act
        val actual = utilities.formatTemperature(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun formatTemperature_validInput_zeroDegrees(){
        //assemble
        val utilities = Utilities()
        val input = 273.0
        val expected = "0°C"

        //act
        val actual = utilities.formatTemperature(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun formatTime_emptyInput_startOfUnixEpochTime(){
        //assemble
        val utilities = Utilities()
        val input = 0L
        val expected = "01 January 1970 01:00:00"

        //act
        val actual = utilities.formatTime(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun formatTime_birthdayMillis_correctFormat(){
        //assemble
        val utilities = Utilities()
        val input = 662152023000
        val expected = "25 December 1990 19:07:03"

        //act
        val actual = utilities.formatTime(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun formatTime_minusValue_backInTime(){
        //assemble
        val utilities = Utilities()
        val input = -662152023000
        val expected = "07 January 1949 04:52:57"

        //act
        val actual = utilities.formatTime(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun direction_minusValue_emptyValue(){
        //assemble
        val utilities = Utilities()
        val input = -90.0f
        val expected = ""

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun direction_outOfBoundsValue_emptyValue(){
        //assemble
        val utilities = Utilities()
        val input = 361.0f
        val expected = ""

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }


    @Test
    fun direction_zeroValue_North(){
        //assemble
        val utilities = Utilities()
        val input = 0.0f
        val expected = "North"

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun direction_threeSixtyValue_North(){
        //assemble
        val utilities = Utilities()
        val input = 360.0f
        val expected = "North"

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }


    @Test
    fun direction_oneEighty_South(){
        //assemble
        val utilities = Utilities()
        val input = 180.0f
        val expected = "South"

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun direction_twoSevenFive_West(){
        //assemble
        val utilities = Utilities()
        val input = 275.0f
        val expected = "West"

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }

    @Test
    fun direction_ninety_East(){
        //assemble
        val utilities = Utilities()
        val input = 90.0f
        val expected = "East"

        //act
        val actual = utilities.direction(input)

        //assert
        assertEquals(expected, actual)
    }



}