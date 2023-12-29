package challenges

import nl.rwslinkman.adventofcode2023.challenges.December23
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December23Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "#.#####################\n" +
            "#.......#########...###\n" +
            "#######.#########.#.###\n" +
            "###.....#.>.>.###.#.###\n" +
            "###v#####.#v#.###.#.###\n" +
            "###.>...#.#.#.....#...#\n" +
            "###v###.#.#.#########.#\n" +
            "###...#.#.#.......#...#\n" +
            "#####.#.#.#######.#.###\n" +
            "#.....#.#.#.......#...#\n" +
            "#.#####.#.#.#########v#\n" +
            "#.#...#...#...###...>.#\n" +
            "#.#.#v#######v###.###v#\n" +
            "#...#.>.#...>.>.#.###.#\n" +
            "#####v#.#.###v#.#.###.#\n" +
            "#.....#...#...#.#.#...#\n" +
            "#.#########.###.#.#.###\n" +
            "#...###...#...#...#.###\n" +
            "###.###.#.###v#####v###\n" +
            "#...#...#.#.>.>.#.>.###\n" +
            "#.###.###.#.###.#.#v###\n" +
            "#.....###...###...#...#\n" +
            "#####################.#"

        val result = December23.part1(sampleData)

        assertEquals(94, result)
    }
}