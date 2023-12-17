package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december14/input.txt")
object December14 : AdventChallenge {

    private const val ROUNDED = 'O'
    private const val CUBE = '#'
    private const val SPACE = '.'

    override fun part1(inputString: String): Any {
        val data = inputString.lines().map { it.toCharArray() }.toTypedArray()

        // Tilt north
        for (row in data.first().indices) {
            var obstacle = -1
            for (col in data.indices) {
                if (data[col][row] == CUBE) {
                    obstacle = col
                }
                if (data[col][row] == ROUNDED) {
                    data[col][row] = SPACE
                    data[++obstacle][row] = ROUNDED
                }
            }
        }

        return data.withIndex().sumOf { (i, row) ->
            val rockCount = row.count { it == ROUNDED }
            val position = data.size - i
            rockCount * position
        }
    }

    override fun part2(inputString: String): Any {
        val cycleCount = 1_000_000_000
        val data = inputString.lines().map { it.toCharArray() }.toTypedArray()
        val cache = mutableMapOf<String, Int>()

        for (cycle in 0 until cycleCount) {
            val cacheKey = data.joinToString("") { it.joinToString("") }

            if (cache.containsKey(cacheKey)) {
                val length = cycle - cache[cacheKey]!!
                val remainingCycles = (cycleCount - cycle) % length
                repeat(remainingCycles) {
                    cycle(data)
                }
                break
            }

            cache[cacheKey] = cycle
            cycle(data)
        }

        return data.withIndex().sumOf { (i, row) ->
            val rockCount = row.count { it == ROUNDED }
            val position = data.size - i
            rockCount * position
        }
    }

    private fun cycle(data: Array<CharArray>) {
        tilt(data, Direction.North)
        tilt(data, Direction.West)
        tilt(data, Direction.South)
        tilt(data, Direction.East)
    }

    private fun tilt(data: Array<CharArray>, direction: Direction) {
        when (direction) {
            Direction.North -> {
                for (col in data.first().indices) {
                    var obstacle = -1
                    for (row in data.indices) {
                        if (data[row][col] == CUBE) {
                            obstacle = row
                        }
                        if (data[row][col] == ROUNDED) {
                            data[row][col] = SPACE
                            data[++obstacle][col] = ROUNDED
                        }
                    }
                }
            }

            Direction.West -> {
                for (row in data.indices) {
                    var obstacle = -1
                    for (col in data.first().indices) {
                        if (data[row][col] == CUBE) {
                            obstacle = col
                        }
                        if (data[row][col] == ROUNDED) {
                            data[row][col] = SPACE
                            data[row][++obstacle] = ROUNDED
                        }
                    }
                }
            }

            Direction.South -> {
                for (col in data.last().indices) {
                    var obstacle = data.size
                    for (row in data.indices.reversed()) {
                        if (data[row][col] == CUBE) {
                            obstacle = row
                        }
                        if (data[row][col] == ROUNDED) {
                            data[row][col] = SPACE
                            data[--obstacle][col] = ROUNDED
                        }
                    }
                }
            }

            Direction.East -> {
                for (row in data.indices) {
                    var obstacle = data.first().size
                    for (col in data.first().indices.reversed()) {
                        if (data[row][col] == CUBE) {
                            obstacle = col
                        }
                        if (data[row][col] == ROUNDED) {
                            data[row][col] = SPACE
                            data[row][--obstacle] = ROUNDED
                        }
                    }
                }
            }
        }
    }

    enum class Direction {
        North,
        West,
        South,
        East
    }
}