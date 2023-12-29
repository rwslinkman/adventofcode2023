package challenges

import nl.rwslinkman.adventofcode2023.challenges.December25
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December25Test {

    @Test
    fun `test part 1`() {
        val sampleData =
            "jqt: rhn xhk nvd\n" +
            "rsh: frs pzl lsr\n" +
            "xhk: hfx\n" +
            "cmg: qnr nvd lhk bvb\n" +
            "rhn: xhk bvb hfx\n" +
            "bvb: xhk hfx\n" +
            "pzl: lsr hfx nvd\n" +
            "qnr: nvd\n" +
            "ntq: jqt hfx bvb xhk\n" +
            "nvd: lhk\n" +
            "lsr: lhk\n" +
            "rzs: qnr cmg lsr rsh\n" +
            "frs: qnr lhk lsr"

        val result = December25.part1(sampleData)

        assertEquals(54, result)
    }
}