package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december07/input.txt")
object December07 : AdventChallenge {

    val cardValues = mapOf(
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 14
    )

    enum class HandStrength {
        FiveOfAKind,
        FourOfAKind,
        FullHouse,
        ThreeOfAKind,
        TwoPair,
        OnePair,
        HighCard
    }

    private val handAndBidFinderRegex = Regex("(\\S{5}) (\\d+)")

    override fun part1(inputString: String): Any {
        val handStrengths = inputString.lines().map {
            val (hand, bid) = handAndBidFinderRegex.find(it)!!.destructured

            val countResult = hand.toCharArray().distinct().map { h ->
                hand.count { c -> c == h }
            }
            val cardStrength = determineCardStrength(countResult)
            Hand(hand, cardStrength, bid.toLong())
        }

        val sorted = handStrengths.sortedWith(HandComparator(cardValues))
        return sorted.foldIndexed(0L) { index, acc, hand ->
            acc + (hand.bid * (index + 1))
        }
    }

    private fun determineCardStrength(countResult: List<Int>): HandStrength {
        val handStrength = if (countResult.contains(5)) {
            HandStrength.FiveOfAKind
        } else if (countResult.contains(4) && countResult.contains(1)) {
            HandStrength.FourOfAKind
        } else if (countResult.contains(3) && countResult.contains(2)) {
            HandStrength.FullHouse
        } else if (countResult.contains(3) && !countResult.contains(2)) {
            HandStrength.ThreeOfAKind
        } else if (countResult.contains(2) && countResult.count { c -> c == 2 } == 2) {
            HandStrength.TwoPair
        } else if (countResult.contains(2) && countResult.count { c -> c == 2 } == 1) {
            HandStrength.OnePair
        } else {
            HandStrength.HighCard
        }
        return handStrength
    }

    data class Hand(val orig: String, val strength: HandStrength, val bid: Long)

    class HandComparator(private val compareCardValues: Map<Char, Int>) : Comparator<Hand> {
        override fun compare(hand1: Hand, hand2: Hand): Int {
            val strCompare = hand2.strength.compareTo(hand1.strength)
            return if (strCompare == 0) {
                var compare = 0
                for (pair in hand1.orig.zip(hand2.orig)) {
                    val o1Val = compareCardValues[pair.first]!!
                    val o2Val = compareCardValues[pair.second]!!
                    compare = o1Val.compareTo(o2Val)
                    if (compare != 0) {
                        break
                    }
                }
                compare
            } else {
                strCompare
            }
        }
    }

    private const val JOKER = 'J'
    val jokerCardValues = mapOf(
        JOKER to 1,
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'Q' to 12,
        'K' to 13,
        'A' to 14
    )

    override fun part2(inputString: String): Any {
        val handStrengths = inputString.lines().map { line ->
            val (hand, bid) = handAndBidFinderRegex.find(line)!!.destructured

            val mostSeenCount = hand.toCharArray().filter { it != JOKER }.groupingBy { it }.eachCount()
            val mostSeenNonJoker = if (mostSeenCount.isNotEmpty()) {
                mostSeenCount.maxBy { it.value }.key
            } else 'A'
            val replacement = hand.replace(JOKER, mostSeenNonJoker)

            val countResult = replacement.groupingBy { it }.eachCount().values.toList()
            val cardStrength = determineCardStrength(countResult)
            Hand(hand, cardStrength, bid.toLong())
        }

        val sorted = handStrengths.sortedWith(HandComparator(jokerCardValues))
        return sorted.foldIndexed(0L) { index, acc, hand ->
            acc + (hand.bid * (index + 1))
        }
    }
}