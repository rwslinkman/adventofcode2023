package challenges

import nl.rwslinkman.adventofcode2023.challenges.December18
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December18Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "R 6 (#70c710)\n" +
            "D 5 (#0dc571)\n" +
            "L 2 (#5713f0)\n" +
            "D 2 (#d2c081)\n" +
            "R 2 (#59c680)\n" +
            "D 2 (#411b91)\n" +
            "L 5 (#8ceee2)\n" +
            "U 2 (#caa173)\n" +
            "L 1 (#1b58a2)\n" +
            "U 2 (#caa171)\n" +
            "R 2 (#7807d2)\n" +
            "U 3 (#a77fa3)\n" +
            "L 2 (#015232)\n" +
            "U 2 (#7a21e3)"

        val result = December18.part1(sampleData)

        assertEquals(62, result)
    }

    @Test
    fun `test part 2`() {
        val sampleData =
            "R 6 (#70c710)\n" +
            "D 5 (#0dc571)\n" +
            "L 2 (#5713f0)\n" +
            "D 2 (#d2c081)\n" +
            "R 2 (#59c680)\n" +
            "D 2 (#411b91)\n" +
            "L 5 (#8ceee2)\n" +
            "U 2 (#caa173)\n" +
            "L 1 (#1b58a2)\n" +
            "U 2 (#caa171)\n" +
            "R 2 (#7807d2)\n" +
            "U 3 (#a77fa3)\n" +
            "L 2 (#015232)\n" +
            "U 2 (#7a21e3)"

        val result = December18.part2(sampleData)

        assertEquals(result, 952408144115L)
    }
}