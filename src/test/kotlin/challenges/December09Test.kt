package challenges

import nl.rwslinkman.adventofcode2023.challenges.December09
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December09Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "0 3 6 9 12 15\n" +
            "1 3 6 10 15 21\n" +
            "10 13 16 21 30 45"

        val result = December09.part1(sampleData)

        assertEquals(114L, result)
    }

    @Test
    fun `test part 2`() {
        val sampleData =
            "0 3 6 9 12 15\n" +
            "1 3 6 10 15 21\n" +
            "10 13 16 21 30 45"

        val result = December09.part2(sampleData)

        assertEquals(2L, result)
    }
}