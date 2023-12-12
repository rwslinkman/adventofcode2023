package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle
import kotlin.math.abs

@Puzzle("december11/input.txt")
object December11 : AdventChallenge {

    private const val SPACE: Char = '.'
    private const val GALAXY: Char = '#'

    override fun part1(inputString: String): Any {
        return calculateSumOfShortestDistances(inputString)
    }

    private fun calculateSumOfShortestDistances(inputString: String, expansionRate: Int = 1): Long {
        val emptyRows = mutableListOf<Int>()
        val emptyCols = mutableListOf<Int>()
        val lines = inputString.lines()
        for (y in lines.indices) {
            if (lines[y].count { it == SPACE } == lines[y].length) {
                emptyRows.add(y)
            }
        }
        for (x in lines[0].indices) {
            if (lines.count { it[x] == SPACE } == lines.size) {
                emptyCols.add(x)
            }
        }

        val galaxyCoords: List<Pair<Int, Int>> = lines.flatMapIndexed { y: Int, line: String ->
            line.mapIndexedNotNull { x, c ->
                if (c == GALAXY) {
                    val newX = x + (emptyCols.count { it < x } * expansionRate)
                    val newY = y + (emptyRows.count { it < y } * expansionRate)
                    Pair(newX, newY)
                } else null
            }
        }

        val shortestDistances = galaxyCoords.flatMapIndexed { idx, first ->
            galaxyCoords.subList(idx + 1, galaxyCoords.size).map { second ->
                distance(first, second)
            }
        }
        return shortestDistances.sum()
    }

    private fun distance(coordinateA: Pair<Int, Int>, coordinateB: Pair<Int, Int>): Long {
        val a = abs(coordinateA.first - coordinateB.first).toLong()
        val b = abs(coordinateA.second - coordinateB.second).toLong()
        return a + b
    }

    override fun part2(inputString: String): Any {
        return calculateSumOfShortestDistances(inputString, 999_999)
    }
}