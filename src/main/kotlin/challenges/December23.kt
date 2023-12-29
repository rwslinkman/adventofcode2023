package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december23/input.txt")
object December23 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val grid = inputString.lines().mapIndexed { y: Int, line: String ->
            line.mapIndexed { x, symbol ->
                Coordinate(x, y, symbol)
            }
        }

        val start = grid.first().first { it.symbol == PATH }
        val end = grid.last().first { it.symbol == PATH }

        val queue = mutableListOf(start)
        val visited = mutableSetOf<Coordinate>()

        while(queue.isNotEmpty()) {
            val currentItem = queue.removeLast()


        }

        return 0
    }

    data class Coordinate(val x: Int, val y: Int, val symbol: Char)

    private const val PATH = '.'

    override fun part2(inputString: String): Any {
        return 0
    }
}