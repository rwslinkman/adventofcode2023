package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december01/input.txt")
object December01: AdventChallenge {

    override fun part1(inputString: String): Any {
        val inputLines = inputString.lines()
        val sumResult = inputLines.sumOf {
            val includedNumbers = it.split("").mapNotNull { c -> c.toIntOrNull() }
            val lineNumber = "${includedNumbers.first()}${includedNumbers.last()}"
            lineNumber.toInt()
        }
        return sumResult
    }

    override fun part2(inputString: String): Any {
        val numberWordMap: Map<String, Int> = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )
        val inputLines = inputString.lines()
        val sumResult = inputLines.sumOf {
            var inputLine = it
            numberWordMap.forEach { (word, num) ->
                var idx = 0
                while (idx >= 0) {
                    idx = inputLine.indexOf(word)
                    if (idx != -1) {
                        val replacement = word.first() + num.toString() + word.last()
                        inputLine = inputLine.replaceRange(idx, idx + 1, replacement)
                    }
                }
            }
            val includedNumbers = inputLine.split("").mapNotNull { c -> c.toIntOrNull() }
            val lineNumber = "${includedNumbers.first()}${includedNumbers.last()}"
            lineNumber.toInt()
        }
        return sumResult
    }
}
