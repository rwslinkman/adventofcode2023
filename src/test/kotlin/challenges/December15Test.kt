package challenges

import nl.rwslinkman.adventofcode2023.challenges.December15
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December15Test {

    @Test
    fun `test part 1 - basic`() {
        val sampleData = "HASH"

        val result = December15.part1(sampleData)

        assertEquals(52L, result)
    }

    @Test
    fun `test part 1 - full`() {
        val sampleData = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

        val result = December15.part1(sampleData)

        assertEquals(1320L, result)
    }

    @Test
    fun `test part 2`() {
        val sampleData = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

        val result = December15.part2(sampleData)

        assertEquals(145, result)
    }
}