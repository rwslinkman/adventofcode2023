package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle
import kotlin.math.ceil
import kotlin.math.pow

@Puzzle("december21/input.txt")
object December21 : AdventChallenge {

    override fun part1(inputString: String): Any {
        return findReachableCoordinates(inputString, stepLimit = 64)
    }

    fun findReachableCoordinates(inputString: String, stepLimit: Int): Int {
        val grid = inputString.lines().flatMapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                Coordinate(x, y, ch)
            }
        }

        val start = grid.first { it.symbol == START }
        var nextQueue = mutableListOf(start)

        for (step in 1..stepLimit) {
            val currentQueue = nextQueue.toMutableList()
            val visited = nextQueue.toMutableSet()
            nextQueue = mutableListOf()

            while (currentQueue.isNotEmpty()) {
                val currentItem = currentQueue.removeFirst()

                for (direction in Direction.entries) {
                    val neighbourX = currentItem.x + direction.dX
                    val neighbourY = currentItem.y + direction.dY

                    val neighbour = grid.find {
                        it.x == neighbourX && it.y == neighbourY && it.symbol != ROCK
                    } ?: continue

                    if (neighbour !in visited) {
                        visited.add(neighbour)
                        nextQueue.add(neighbour)
                    }
                }
            }
        }

        return nextQueue.size
    }

    private const val GARDEN: Char = '.'
    private const val ROCK: Char = '#'
    private const val START: Char = 'S'

    data class Coordinate(val x: Int, val y: Int, val symbol: Char)

    enum class Direction(val dX: Int, val dY: Int) {
        North(0, -1),
        East(1, 0),
        South(0, 1),
        West(-1, 0)
    }

    override fun part2(inputString: String): Any {
        val grid = inputString.lines().flatMapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                Coordinate(x, y, ch)
            }
        }

        val start = grid.first { it.symbol == START }
        val height = grid.groupBy { it.y }.size
        val width = grid.groupBy { it.x }.size
        val mod = totalStepsNeeded % height
        val runs = listOf(mod, (mod + height), (mod + (height * 2)))

        val seenStates = mutableListOf<Int>()

        for(run in runs) {
            var nextQueue = mutableListOf(start)
            for(r in 0..<run) {
                val currentQueue = nextQueue.toMutableList()
                val visited = nextQueue.toMutableSet()
                nextQueue = mutableListOf()

                while(currentQueue.isNotEmpty()) {
                    val currentItem = currentQueue.removeFirst()

                    for(direction in Direction.entries) {
                        val neighbourX = (currentItem.x + direction.dX) % width
                        val neighbourY = (currentItem.y + direction.dY) % height

                        val neighbour = grid.find {
                            it.x == neighbourX && it.y == neighbourY && it.symbol != ROCK
                        } ?: continue

                        if (neighbour !in visited) {
                            visited.add(neighbour)
                            nextQueue.add(neighbour)
                        }
                    }
                }
            }
            seenStates.add(nextQueue.size)
        }

//        val seenStates = mutableListOf(3849, 7623, 7558)

        println(seenStates)
        val m = seenStates[1] - seenStates[0]
        val n = seenStates[2] - seenStates[1]
        val a = (n - m).floorDiv(2)
        val b = m - (3 * a)
        val c = seenStates[0] - b - a

        val ceiling = ceil(totalStepsNeeded / height.toDouble())
        return a * (ceiling.pow(ceiling)) + b * ceiling + c



        return 0
    }

    private const val totalStepsNeeded = 26_501_365
}