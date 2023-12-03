package challenges

import nl.rwslinkman.adventofcode2023.challenges.December03
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December03Test {

    @Test
    fun `test challenge 1`() {
        val sampleData: String =
            "467..114..\n" +
            "...*......\n" +
            "..35..633.\n" +
            "......#...\n" +
            "617*......\n" +
            ".....+.58.\n" +
            "..592.....\n" +
            "......755.\n" +
            "...$.*....\n" +
            ".664.598.."

        val result = December03.part1(sampleData)

        assertEquals(4361, result)
    }

    @Test
    fun `test challenge 2`() {
        val sampleData: String =
            "467..114..\n" +
            "...*......\n" +
            "..35..633.\n" +
            "......#...\n" +
            "617*......\n" +
            ".....+.58.\n" +
            "..592.....\n" +
            "......755.\n" +
            "...\$.*....\n" +
            ".664.598.."

        val result = December03.part2(sampleData)

        assertEquals(467835, result)
    }
}