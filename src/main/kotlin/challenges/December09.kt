package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december09/input.txt")
object December09 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val lineData = inputString.lines().map { line ->
            line.split(" ").map { it.toLong() }.toList()
        }

        val historyValues = calculateHistoryValues(lineData)
        return historyValues.sum()
    }

    private fun calculateHistoryValues(lineData: List<List<Long>>) = lineData.map { numbers ->
        val stack = ArrayDeque<List<Long>>()
        stack.add(numbers)
        // Difference between numbers
        var diffNumbers = numbers
            .windowed(2, 1)
            .map { window -> window[1] - window[0] }
        var isAllZeroes = diffNumbers.all { it == 0L }
        stack.add(diffNumbers)
        // Continue until you find a list of zeroes
        while (!isAllZeroes) {
            diffNumbers = diffNumbers
                .windowed(2, 1)
                .map { window -> window[1] - window[0] }
            isAllZeroes = diffNumbers.all { it == 0L }
            stack.add(diffNumbers)
        }

        // Reverse stack to find number
        var lastDiff = stack.removeFirst().lastOrNull() ?: 0
        var leftNumber = stack.removeFirst().lastOrNull() ?: 0
        var placeHolder: Long
        while (stack.isNotEmpty()) {
            placeHolder = leftNumber + lastDiff

            lastDiff = placeHolder
            leftNumber = stack.removeFirst().lastOrNull() ?: 0
        }
        leftNumber + lastDiff
    }

    override fun part2(inputString: String): Any {
        val lineData = inputString.lines().map { line ->
            line.split(" ").map { it.toLong() }.toList().reversed()
        }

        val historyValues = calculateHistoryValues(lineData)
        return historyValues.sum()
    }
}