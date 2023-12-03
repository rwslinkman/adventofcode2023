package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december03/input.txt")
object December03 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val (foundSymbols, foundNumbers) = parseInput(inputString)
        val possibleCoordinates: Set<Coordinate> = foundSymbols.flatMap {
            it.coord.allSides()
        }.toSet()
        return foundNumbers.filter {
            it.coords.any { c -> possibleCoordinates.contains(c) }
        }.sumOf { it.num }
    }

    private fun parseInput(inputString: String): Pair<MutableList<Symbol>, List<FoundNumber>> {
        val findSymbolsRegex = Regex("([^0-9.\\s])")
        val findNumbersRegex = Regex("([0-9]+)")

        val foundSymbols = mutableListOf<Symbol>()
        val foundNumbers = inputString.lines().flatMapIndexed { y, line ->
            // Extract symbols separately, remembering their coordinate
            findSymbolsRegex.findAll(line).forEach { found ->
                val symbolCoord = Coordinate(found.range.first, y)
                foundSymbols.add(Symbol(found.value, symbolCoord))
            }
            // Gather all numbers (with coordinates) in a list
            val lineNumbers = findNumbersRegex.findAll(line).toList().map {
                val numberCoords = it.range.map { x ->
                    Coordinate(x, y)
                }
                FoundNumber(it.value.toInt(), numberCoords)
            }
            // result of 'map'
            lineNumbers
        }
        return Pair(foundSymbols, foundNumbers)
    }

    override fun part2(inputString: String): Any {
        val (foundSymbols, foundNumbers) = parseInput(inputString)
        val foundGears = foundSymbols.filter { it.symbol == GEAR_SYMBOL }

        return foundGears.mapNotNull {
            val gearSides = it.coord.allSides()
            val numbersWithGear = foundNumbers.filter { fn -> gearSides.any { c -> fn.coords.contains(c) } }
            if (numbersWithGear.size == 2) {
                numbersWithGear[0].num * numbersWithGear[1].num
            } else null
        }.sum()
    }

    private const val GEAR_SYMBOL = "*"

    data class Coordinate(val x: Int, val y: Int) {
        fun left(): Coordinate = Coordinate(this.x - 1, this.y)
        fun leftTop(): Coordinate = Coordinate(this.x - 1, this.y - 1)
        fun top(): Coordinate = Coordinate(this.x, this.y - 1)
        fun rightTop(): Coordinate = Coordinate(this.x + 1, this.y - 1)
        fun right(): Coordinate = Coordinate(this.x + 1, this.y)
        fun rightBottom(): Coordinate = Coordinate(this.x + 1, this.y + 1)
        fun bottom(): Coordinate = Coordinate(this.x, this.y + 1)
        fun leftBottom(): Coordinate = Coordinate(this.x - 1, this.y + 1)

        fun allSides(): List<Coordinate> = listOf(
            left(),
            leftTop(),
            top(),
            rightTop(),
            right(),
            rightBottom(),
            bottom(),
            leftBottom(),
        )
    }

    data class Symbol(val symbol: String, val coord: Coordinate)

    data class FoundNumber(val num: Int, val coords: List<Coordinate>)
}