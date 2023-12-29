package challenges

import nl.rwslinkman.adventofcode2023.challenges.December22
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December22Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "1,0,1~1,2,1\n" +
            "0,0,2~2,0,2\n" +
            "0,2,3~2,2,3\n" +
            "0,0,4~0,2,4\n" +
            "2,0,5~2,2,5\n" +
            "0,1,6~2,1,6\n" +
            "1,1,8~1,1,9"

        val result = December22.part1(sampleData)

        assertEquals(5, result)
    }
}