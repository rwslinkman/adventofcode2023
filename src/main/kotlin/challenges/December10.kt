package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle
import java.util.*

@Puzzle("december10/input.txt")
object December10 : AdventChallenge {
    override fun part1(inputString: String): Any {
        val pipes: List<Pipe> = parsePipeNetwork(inputString)

        val startPipe = pipes.first { it.symbol == Symbol.Start }
        val options = findFirstNeighbours(pipes, startPipe)
        val hasSeen = mutableSetOf(startPipe)
        var maxDistance = 0
        while (options.isNotEmpty()) {
            val pipe = options.removeLast()
            hasSeen.add(pipe)
            maxDistance++

            // Find next options
            val possiblePipeEnds: List<Pipe> = findPossiblePipeEnds(pipe, pipes)
            possiblePipeEnds.forEach {
                if (!hasSeen.contains(it)) {
                    options.add(it)
                }
            }
        }
        return maxDistance / 2
    }

    private fun findFirstNeighbours(
        pipes: List<Pipe>,
        startPipe: Pipe
    ): ArrayDeque<Pipe> {
        val neighbourWest = pipes.find {
            it.x == startPipe.x - 1 && it.y == startPipe.y && listOf(
                Symbol.EastWest,
                Symbol.NorthEast,
                Symbol.SouthEast
            ).contains(it.symbol)
        }
        val neighbourEast = pipes.find {
            it.x == startPipe.x + 1 && it.y == startPipe.y && listOf(
                Symbol.EastWest,
                Symbol.NorthWest,
                Symbol.SouthWest
            ).contains(it.symbol)
        }
        val neighbourNorth = pipes.find {
            it.x == startPipe.x && it.y == startPipe.y - 1 && listOf(
                Symbol.NorthSouth,
                Symbol.SouthWest,
                Symbol.SouthEast
            ).contains(it.symbol)
        }
        val neighbourSouth = pipes.find {
            it.x == startPipe.x && it.y == startPipe.y + 1 && listOf(
                Symbol.NorthSouth,
                Symbol.NorthWest,
                Symbol.NorthEast
            ).contains(it.symbol)
        }
        return ArrayDeque(
            listOfNotNull(
                neighbourWest,
                neighbourEast,
                neighbourNorth,
                neighbourSouth,
            )
        )
    }

    data class Pipe(val x: Int, val y: Int, val symbol: Symbol)

    enum class Symbol(val symbol: String) {
        NorthSouth("|"),
        EastWest("-"),
        NorthEast("L"),
        NorthWest("J"),
        SouthWest("7"),
        SouthEast("F"),
        Ground("."),
        Start("S");

        companion object {
            fun parse(str: String): Symbol {
                return entries.first { str == it.symbol }
            }
        }
    }

    override fun part2(inputString: String): Any {
        val pipes: List<Pipe> = parsePipeNetwork(inputString)

        val startPipe = pipes.first { it.symbol == Symbol.Start }
        val options = findFirstNeighbours(pipes, startPipe)
        val hasSeen = mutableSetOf(startPipe)
        while (options.isNotEmpty()) {
            val pipe = options.removeLast()
            hasSeen.add(pipe)

            // Find next options
            val possiblePipeEnds: List<Pipe> = findPossiblePipeEnds(pipe, pipes)
            possiblePipeEnds.forEach {
                if (!hasSeen.contains(it)) {
                    options.add(it)
                }
            }
        }

        val cleanMap = pipes
            .map {
                if (hasSeen.contains(it)) {
                    it
                } else {
                    Pipe(it.x, it.y, Symbol.Ground)
                }
            }
            .groupBy { it.y }
            .map { it.value.sortedBy { lineItem -> lineItem.x } }

        var areaCount = 0
        cleanMap.forEach { line ->
            var inside = false
            var pos = 0
            while (pos < line.size) {
                val currentSymbol = line[pos].symbol
                if (currentSymbol == Symbol.Ground && inside) {
                    areaCount++
                    pos++
                } else if (currentSymbol == Symbol.NorthSouth || currentSymbol == Symbol.Start) {
                    inside = !inside
                    pos++
                } else if (listOf(Symbol.EastWest, Symbol.Ground).contains(currentSymbol)) {
                    pos++
                } else {
                    val blockStart = line[pos]
                    pos++
                    while (line[pos].symbol == Symbol.EastWest) {
                        pos++
                    }
                    val blockEnd = line[pos]

                    val option = "${blockStart.symbol.symbol}${blockEnd.symbol.symbol}"
                    if (listOf("L7", "FJ").contains(option)) {
                        inside = !inside
                    }
                    pos++
                }
            }
        }
        return areaCount
    }

    private fun parsePipeNetwork(inputString: String): List<Pipe> {
        val pipes: List<Pipe> = inputString.lines().flatMapIndexed { y: Int, line: String ->
            line.mapIndexed { x, character ->
                Pipe(x, y, Symbol.parse(character.toString()))
            }
        }
        return pipes
    }

    private fun findPossiblePipeEnds(
        pipe: Pipe,
        pipes: List<Pipe>
    ) = when (pipe.symbol) {
        Symbol.NorthSouth -> listOfNotNull(
            pipes.find { it.x == pipe.x && it.y == pipe.y - 1 },
            pipes.find { it.x == pipe.x && it.y == pipe.y + 1 },
        )

        Symbol.EastWest -> listOfNotNull(
            pipes.find { it.x == pipe.x + 1 && it.y == pipe.y },
            pipes.find { it.x == pipe.x - 1 && it.y == pipe.y },
        )

        Symbol.SouthWest -> listOfNotNull(
            pipes.find { it.x == pipe.x && it.y == pipe.y + 1 },
            pipes.find { it.x == pipe.x - 1 && it.y == pipe.y },
        )

        Symbol.NorthWest -> listOfNotNull(
            pipes.find { it.x == pipe.x && it.y == pipe.y - 1 },
            pipes.find { it.x == pipe.x - 1 && it.y == pipe.y },
        )

        Symbol.NorthEast -> listOfNotNull(
            pipes.find { it.x == pipe.x && it.y == pipe.y - 1 },
            pipes.find { it.x == pipe.x + 1 && it.y == pipe.y },
        )

        Symbol.SouthEast -> listOfNotNull(
            pipes.find { it.x == pipe.x && it.y == pipe.y + 1 },
            pipes.find { it.x == pipe.x + 1 && it.y == pipe.y },
        )

        Symbol.Start, Symbol.Ground -> listOf()

    }
}