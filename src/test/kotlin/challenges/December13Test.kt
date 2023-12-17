package challenges

import nl.rwslinkman.adventofcode2023.challenges.December13
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December13Test {

    @Test
    fun `test part 1`() {
        val sampleData: String =
            "#.##..##.\n" +
            "..#.##.#.\n" +
            "##......#\n" +
            "##......#\n" +
            "..#.##.#.\n" +
            "..##..##.\n" +
            "#.#.##.#.\n" +
            "\n" +
            "#...##..#\n" +
            "#....#..#\n" +
            "..##..###\n" +
            "#####.##.\n" +
            "#####.##.\n" +
            "..##..###\n" +
            "#....#..#"

        val result = December13.part1(sampleData)

        assertEquals(405L, result)
    }

    @Test
    fun `test part 2`() {
        val sampleData: String =
            "#.##..##.\n" +
            "..#.##.#.\n" +
            "##......#\n" +
            "##......#\n" +
            "..#.##.#.\n" +
            "..##..##.\n" +
            "#.#.##.#.\n" +
            "\n" +
            "#...##..#\n" +
            "#....#..#\n" +
            "..##..###\n" +
            "#####.##.\n" +
            "#####.##.\n" +
            "..##..###\n" +
            "#....#..#"

        val result = December13.part2(sampleData)

        assertEquals(400L, result)
    }
}