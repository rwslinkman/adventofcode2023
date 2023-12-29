package challenges

import nl.rwslinkman.adventofcode2023.challenges.December21
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December21Test {

    @Test
    fun `test part 1`() {
        val sampleData: String =
            "...........\n" +
            ".....###.#.\n" +
            ".###.##..#.\n" +
            "..#.#...#..\n" +
            "....#.#....\n" +
            ".##..S####.\n" +
            ".##..#...#.\n" +
            ".......##..\n" +
            ".##.#.####.\n" +
            ".##..##.##.\n" +
            "..........."

        val result = December21.findReachableCoordinates(sampleData, stepLimit = 6)

        assertEquals(16, result)
    }

    @Test
    fun `test part 2`() {
        // Grid is now infinite size
        val sampleData: String =
            "...........\n" +
            ".....###.#.\n" +
            ".###.##..#.\n" +
            "..#.#...#..\n" +
            "....#.#....\n" +
            ".##..S####.\n" +
            ".##..#...#.\n" +
            ".......##..\n" +
            ".##.#.####.\n" +
            ".##..##.##.\n" +
            "..........."

        val result = December21.part2(sampleData)

        assertNotEquals(0, result)
    }

}