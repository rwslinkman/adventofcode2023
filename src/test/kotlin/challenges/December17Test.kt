package challenges

import nl.rwslinkman.adventofcode2023.challenges.December17
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December17Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "2413432311323\n" +
            "3215453535623\n" +
            "3255245654254\n" +
            "3446585845452\n" +
            "4546657867536\n" +
            "1438598798454\n" +
            "4457876987766\n" +
            "3637877979653\n" +
            "4654967986887\n" +
            "4564679986453\n" +
            "1224686865563\n" +
            "2546548887735\n" +
            "4322674655533"

        val result = December17.part1(sampleData)

        assertEquals(102, result)
    }

    @Test
    fun `test part 1 - Reddit suggestion`() {
        val redditExample =
            "112999\n" +
            "911111"

        val result = December17.part1(redditExample)

        assertEquals(7, result)
    }
}