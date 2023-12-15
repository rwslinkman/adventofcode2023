package challenges

import nl.rwslinkman.adventofcode2023.challenges.December12
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December12Test {

    @Test
    fun `test part 1`() {
        val sampleData: String =
            "???.### 1,1,3\n" +
            ".??..??...?##. 1,1,3\n" +
            "?#?#?#?#?#?#?#? 1,3,1,6\n" +
            "????.#...#... 4,1,1\n" +
            "????.######..#####. 1,6,5\n" +
            "?###???????? 3,2,1"

        val result = December12.part1(sampleData)

        assertEquals(21, result)
    }
}