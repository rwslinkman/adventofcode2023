package challenges

import nl.rwslinkman.adventofcode2023.challenges.December20
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December20Test {

    @Test
    fun `test part 1 - simple`() {
        val sampleData =
            "broadcaster -> a, b, c\n" +
            "%a -> b\n" +
            "%b -> c\n" +
            "%c -> inv\n" +
            "&inv -> a"

        val result = December20.part1(sampleData)

        assertEquals(32000000L, result)
    }

    @Test
    fun `test part 1 - complicated`() {
        val sampleData =
            "broadcaster -> a\n" +
            "%a -> inv, con\n" +
            "&inv -> b\n" +
            "%b -> con\n" +
            "&con -> output"

        val result = December20.part1(sampleData)

        assertEquals(11687500L, result)
    }
}