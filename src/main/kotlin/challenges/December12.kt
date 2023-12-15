package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december12/input.txt")
object December12 : AdventChallenge {

    private const val Operational = '.'
    private const val Damaged = '#'
    private const val Unknown = '?'

    override fun part1(inputString: String): Any {
        val data = inputString.lines().map { line ->
            val splitLine = line.split(" ")
            val condition = splitLine.first()
            val contiguousGroups = splitLine.last().split(",").map { it.toInt() }.toList()
            Pair(condition, contiguousGroups)
        }

        // TODO: Solve puzzle of Day 12 - part 1
        return 0
    }

    override fun part2(inputString: String): Any {
        // TODO: Solve puzzle of Day 12 - part 1
        return 0
    }
}