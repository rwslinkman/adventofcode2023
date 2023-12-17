package challenges

import nl.rwslinkman.adventofcode2023.challenges.December14
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December14Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "O....#....\n" +
            "O.OO#....#\n" +
            ".....##...\n" +
            "OO.#O....O\n" +
            ".O.....O#.\n" +
            "O.#..O.#.#\n" +
            "..O..#O..O\n" +
            ".......O..\n" +
            "#....###..\n" +
            "#OO..#...."

        val result = December14.part1(sampleData)

        assertEquals(136, result)
    }

    @Test
    fun `test part 2`() {
        val sampleData =
            "O....#....\n" +
            "O.OO#....#\n" +
            ".....##...\n" +
            "OO.#O....O\n" +
            ".O.....O#.\n" +
            "O.#..O.#.#\n" +
            "..O..#O..O\n" +
            ".......O..\n" +
            "#....###..\n" +
            "#OO..#...."

        val result = December14.part2(sampleData)

        assertEquals(64, result)
    }
}