package challenges

import nl.rwslinkman.adventofcode2023.challenges.December11
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December11Test {

    @Test
    fun `test challenge 1`() {
        val sampleData: String =
            "...#......\n" +
            ".......#..\n" +
            "#.........\n" +
            "..........\n" +
            "......#...\n" +
            ".#........\n" +
            ".........#\n" +
            "..........\n" +
            ".......#..\n" +
            "#...#....."

        val result = December11.part1(sampleData)

        assertEquals(374, result)
    }
}