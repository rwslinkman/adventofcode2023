package challenges

import nl.rwslinkman.adventofcode2023.challenges.December16
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December16Test {

    @Test
    fun `test part 1`() {
        val sampleData: String = """
                .|...\....
                |.-.\.....
                .....|-...
                ........|.
                ..........
                .........\
                ..../.\\..
                .-.-/..|..
                .|....-|.\
                ..//.|....
            """.trimIndent()

        val result = December16.part1(sampleData)

        assertEquals(46, result)
    }

    @Test
    fun `test part 2`() {
        val sampleData: String = """
                .|...\....
                |.-.\.....
                .....|-...
                ........|.
                ..........
                .........\
                ..../.\\..
                .-.-/..|..
                .|....-|.\
                ..//.|....
            """.trimIndent()

        val result = December16.part2(sampleData)

        assertEquals(51, result)
    }
}