package challenges

import nl.rwslinkman.adventofcode2023.challenges.December07
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class December07Test {

    @Test
    fun `test challenge 1`() {
        val sampleData: String =
            "32T3K 765\n" +
            "T55J5 684\n" +
            "KK677 28\n" +
            "KTJJT 220\n" +
            "QQQJA 483"


        val result = December07.part1(sampleData)

        assertEquals(6440L, result)
    }

    @Test
    fun `test compare`() {
        val comp = December07.HandComparator(December07.cardValues)

        val h1 = December07.Hand("KK677", December07.HandStrength.TwoPair, 28)
        val h2 = December07.Hand("KTJJT", December07.HandStrength.TwoPair, 28)
        val result = comp.compare(h1, h2)
        assertEquals(1, result)

        val h3 = December07.Hand("T55J5", December07.HandStrength.ThreeOfAKind, 28)
        val h4 = December07.Hand("QQQJA", December07.HandStrength.ThreeOfAKind, 28)
        val result2 = comp.compare(h3, h4)
        assertEquals(-1, result2)

        val h5 = December07.Hand("K2677", December07.HandStrength.TwoPair, 28)
        val h6 = December07.Hand("KJJJT", December07.HandStrength.TwoPair, 28)
        val result3 = comp.compare(h5, h6)
        assertEquals(-1, result3)
    }

    @Test
    fun `test challenge 2`() {
        val sampleData: String =
            "32T3K 765\n" +
            "T55J5 684\n" +
            "KK677 28\n" +
            "KTJJT 220\n" +
            "QQQJA 483"
//        val sampleData: String = "JJJJJ 765"

        val result = December07.part2(sampleData)

        assertEquals(5905L, result)
    }

    @Test
    fun `test compare2`() {
        val comp = December07.HandComparator(December07.jokerCardValues)

        val h = December07.Hand("KK677", December07.HandStrength.TwoPair, 28)
        val h0 = December07.Hand("KTJJT", December07.HandStrength.TwoPair, 28)
        val result0 = comp.compare(h, h0)
        assertEquals(1, result0)

        val h1 = December07.Hand("K2677", December07.HandStrength.TwoPair, 28)
        val h2 = December07.Hand("KJJJT", December07.HandStrength.TwoPair, 28)
        val result1 = comp.compare(h1, h2)
        assertEquals(1, result1)

        val h3 = December07.Hand("T55J5", December07.HandStrength.ThreeOfAKind, 28)
        val h4 = December07.Hand("QQQJA", December07.HandStrength.ThreeOfAKind, 28)
        val result2 = comp.compare(h3, h4)
        assertEquals(-1, result2)

        val h5 = December07.Hand("JKKK2", December07.HandStrength.FourOfAKind, 1)
        val h6 = December07.Hand("QQQQ2", December07.HandStrength.FourOfAKind, 1)
        val result3 = comp.compare(h5, h6)
        assertEquals(-1, result3)
    }
}