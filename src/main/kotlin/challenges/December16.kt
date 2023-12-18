package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle
import nl.rwslinkman.adventofcode2023.challenges.December16.Direction.*
import nl.rwslinkman.adventofcode2023.challenges.December16.Symbol.*

@Puzzle("december16/input.txt")
object December16 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val grid = parseInputToCoordinates(inputString)

        val start = Beam(East, 0, 0)
        val options = mutableListOf(start)
        val visited = mutableSetOf<Beam>()

        while (options.isNotEmpty()) {
            val currentItem = options.removeLast()
            visited.add(currentItem)

            val nextCoord = grid.firstOrNull { it.x == currentItem.x && it.y == currentItem.y }
            val newOptions = if (nextCoord != null) {
                getNextNeighbours(grid, currentItem.direction, nextCoord)
            } else emptyList()

            val validOptions = newOptions.filter { it !in visited }
            options.addAll(validOptions)
        }
        return visited.distinctBy { Pair(it.x, it.y) }.count()
    }

    private fun parseInputToCoordinates(inputString: String): List<Coordinate> {
        return inputString.lines().flatMapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, space ->
                val symbol = Symbol.parse(space)
                Coordinate(symbol, x, y)
            }
        }
    }

    private fun getNextNeighbours(
        data: List<Coordinate>,
        currentDirection: Direction,
        nextCoordinate: Coordinate
    ): List<Beam> {
        return when (nextCoordinate.symbol) {
            MIRROR_L -> when (currentDirection) {
                East -> findBeam(data, nextCoordinate, South)
                West -> findBeam(data, nextCoordinate, North)
                South -> findBeam(data, nextCoordinate, East)
                North -> findBeam(data, nextCoordinate, West)
            }

            MIRROR_R -> when (currentDirection) {
                East -> findBeam(data, nextCoordinate, North)
                West -> findBeam(data, nextCoordinate, South)
                North -> findBeam(data, nextCoordinate, East)
                South -> findBeam(data, nextCoordinate, West)
            }

            SPLITTER_H -> when (currentDirection) {
                East -> findBeam(data, nextCoordinate, East)
                West -> findBeam(data, nextCoordinate, West)
                North, South -> listOf(
                    findBeam(data, nextCoordinate, West),
                    findBeam(data, nextCoordinate, East)
                ).flatten()
            }

            SPLITTER_V -> when (currentDirection) {
                East, West -> listOf(
                    findBeam(data, nextCoordinate, North),
                    findBeam(data, nextCoordinate, South),
                ).flatten()

                North -> findBeam(data, nextCoordinate, North)
                South -> findBeam(data, nextCoordinate, South)
            }

            EMPTY -> when (currentDirection) {
                East -> findBeam(data, nextCoordinate, East)
                West -> findBeam(data, nextCoordinate, West)
                North -> findBeam(data, nextCoordinate, North)
                South -> findBeam(data, nextCoordinate, South)
            }
        }
    }

    private fun findBeam(data: List<Coordinate>, orig: Coordinate, direction: Direction): List<Beam> {
        return data.filter {
            it.x == (orig.x + direction.diffX) && it.y == (orig.y + direction.diffY)
        }
            .map {
                Beam(direction, it.x, it.y)
            }
    }

    data class Coordinate(val symbol: Symbol, val x: Int, val y: Int)
    data class Beam(val direction: Direction, val x: Int, val y: Int)

    enum class Direction(val diffX: Int, val diffY: Int) {
        East(1, 0),
        West(-1, 0),
        North(0, -1),
        South(0, 1)
    }

    enum class Symbol(val value: Char) {
        MIRROR_L('\\'),
        MIRROR_R('/'),
        SPLITTER_H('-'),
        SPLITTER_V('|'),
        EMPTY('.');

        companion object {
            fun parse(space: Char): Symbol = entries.first { it.value == space }
        }
    }

    override fun part2(inputString: String): Any {
        val grid = parseInputToCoordinates(inputString)

        val maxX = grid.maxBy { it.x }.x
        val maxY = grid.maxBy { it.y }.y
        val starts = mutableSetOf<Beam>()
        for (x in 0..maxX) {
            starts.add(Beam(South, x, 0))
            starts.add(Beam(North, x, maxY))
        }
        for (y in 0..maxY) {
            for (d in Direction.entries) {
                starts.add(Beam(East, 0, y))
                starts.add(Beam(West, maxX, y))
            }
        }

        return starts.maxOf { start ->
            val options = mutableListOf(start)
            val visited = mutableSetOf<Beam>()
            while (options.isNotEmpty()) {
                val currentItem = options.removeLast()
                visited.add(currentItem)

                val nextCoord = grid.firstOrNull { it.x == currentItem.x && it.y == currentItem.y }
                val newOptions = if (nextCoord != null) {
                    getNextNeighbours(grid, currentItem.direction, nextCoord)
                } else emptyList()

                val validOptions = newOptions.filter { it !in visited }
                options.addAll(validOptions)
            }
            visited.distinctBy { Pair(it.x, it.y) }.count()
        }
    }
}