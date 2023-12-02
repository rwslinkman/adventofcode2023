package challenges

import nl.rwslinkman.adventofcode2023.challenges.December01
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December01Test {

    @Test
    fun `test challenge 1`() {
        val sampleInput =
            "1abc2\n" +
            "pqr3stu8vwx\n" +
            "a1b2c3d4e5f\n" +
            "treb7uchet"

        val result = December01.sumOfDigits(sampleInput)

        assertEquals(142, result)
    }

    @Test
    fun `test challenge 2`() {
        val sampleInput =
            "two1nine\n" +
            "eightwothree\n" +
            "abcone2threexyz\n" +
            "xtwone3four\n" +
            "4nineeightseven2\n" +
            "zoneight234\n" +
            "7pqrstsixteen"

        val result = December01.sumOfDigitsAndWords(sampleInput)

        assertEquals(281, result)
    }
}