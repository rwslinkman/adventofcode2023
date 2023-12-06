package challenges

import nl.rwslinkman.adventofcode2023.challenges.December06
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December06Test {

    @Test
    fun `test challenge 1`() {
        val sampleData: String =
            "Time:      7  15   30\n" +
            "Distance:  9  40  200"

        val result = December06.part1(sampleData)

        assertEquals(288, result)
    }

    @Test
    fun `test challenge 2`() {
        val sampleData: String =
            "Time:      7  15   30\n" +
            "Distance:  9  40  200"

        val result = December06.part2(sampleData)

        assertEquals(71503, result)
    }
}