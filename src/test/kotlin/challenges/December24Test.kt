package challenges

import nl.rwslinkman.adventofcode2023.challenges.December24
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December24Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "19, 13, 30 @ -2,  1, -2\n" +
            "18, 19, 22 @ -1, -1, -2\n" +
            "20, 25, 34 @ -2, -2, -4\n" +
            "12, 31, 28 @ -1, -2, -1\n" +
            "20, 19, 15 @  1, -5, -3"

        val result = December24.countCollisions(sampleData, 7, 27)

        assertEquals(2, result)
    }
}