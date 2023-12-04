package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december04/input.txt")
object December04 : AdventChallenge {
    override fun part1(inputString: String): Any {
        val scratchCards = parseStringToScratchCards(inputString)

        return scratchCards.sumOf { card ->
            val matches = card.myNumbers.count { card.winningNumbers.contains(it) }
            if (matches == 0) {
                0
            } else {
                var score = 1
                repeat(matches - 1) {
                    score *= 2
                }
                score
            }
        }
    }

    private fun parseStringToScratchCards(inputString: String): List<ScratchCard> {
        val parserRegex = Regex("Card\\s+(\\d+):\\s+(\\d+\\s+\\d+(?:\\s+\\d+)*?)\\s+\\|\\s+(\\d+\\s+\\d+(?:\\s+\\d+)*)")
        val scratchCards = inputString.lines().map {
            val (cardId, winningNumbersLine, myNumbersLine) = parserRegex.find(it)!!.destructured

            val winningNumbers = winningNumbersLine.split(" ").mapNotNull { num -> num.toIntOrNull() }
            val myNumbers = myNumbersLine.split(" ").mapNotNull { num -> num.toIntOrNull() }
            ScratchCard(cardId.toInt(), winningNumbers, myNumbers)
        }
        return scratchCards
    }

    override fun part2(inputString: String): Any {
        val scratchCards = parseStringToScratchCards(inputString)

        val cardCountMap = mutableMapOf<Int, Int>()
        scratchCards.forEach { card ->
            val matches = card.myNumbers.count { card.winningNumbers.contains(it) }
            val copies = cardCountMap[card.cardId] ?: 1
            cardCountMap[card.cardId] = copies

            repeat(matches) {
                val nextCardId = card.cardId + (it + 1)
                cardCountMap[nextCardId] = (cardCountMap[nextCardId] ?: 1) + copies
            }
        }
        return cardCountMap.values.sum()
    }

    data class ScratchCard(val cardId: Int, val winningNumbers: List<Int>, val myNumbers: List<Int>)
}