package challenges

import nl.rwslinkman.adventofcode2023.challenges.December08
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class December08Test {

    @Test
    fun part1() {
        val sampleData =
            "RL\n" +
            "\n" +
            "AAA = (BBB, CCC)\n" +
            "BBB = (DDD, EEE)\n" +
            "CCC = (ZZZ, GGG)\n" +
            "DDD = (DDD, DDD)\n" +
            "EEE = (EEE, EEE)\n" +
            "GGG = (GGG, GGG)\n" +
            "ZZZ = (ZZZ, ZZZ)"

        val result = December08.part1(sampleData)

        assertEquals(2, result)
    }

    @Test
    fun part1a() {
        val sampleData: String =
            "LLR\n" +
            "\n" +
            "AAA = (BBB, BBB)\n" +
            "BBB = (AAA, ZZZ)\n" +
            "ZZZ = (ZZZ, ZZZ)"

        val result = December08.part1(sampleData)

        assertEquals(6, result)
    }

    @Test
    fun part2() {
        val sampleData =
            "LR\n" +
            "\n" +
            "11A = (11B, XXX)\n" +
            "11B = (XXX, 11Z)\n" +
            "11Z = (11B, XXX)\n" +
            "22A = (22B, XXX)\n" +
            "22B = (22C, 22C)\n" +
            "22C = (22Z, 22Z)\n" +
            "22Z = (22B, 22B)\n" +
            "XXX = (XXX, XXX)"

        val result = December08.part2(sampleData)

        assertEquals(6L, result)
    }
}