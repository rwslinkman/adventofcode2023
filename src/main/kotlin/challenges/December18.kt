package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle
import kotlin.math.max
import kotlin.math.min

@Puzzle("december18/input.txt")
object December18 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val digPlanRegex = Regex("([UDLR]) (\\d+) \\(([#0-9a-z]+)\\)")
        val digPlan = inputString.lines().map {
            val (directionStr, distanceStr, edgeColorHex) = digPlanRegex.find(it)!!.destructured
            val direction = Direction.parse(directionStr)
            DigInstruction(direction, distanceStr.toInt(), edgeColorHex)
        }

        // Find all edges based on given dig plan
        val start = Coordinate(0, 0)
        var currentLocation = start
        val edges = mutableListOf(start)
        digPlan.forEach {
            // Run each instruction to find edges
            for (d in 1..it.distance) {
                val nextEdgeLocation = Coordinate(
                    currentLocation.x + it.direction.dX,
                    currentLocation.y + it.direction.dY
                )
                edges.add(nextEdgeLocation)
                currentLocation = nextEdgeLocation
            }
        }

        // In entire grid, find all coordinates inside edges
        val minY = edges.minBy { it.y }.y
        val minX = edges.minBy { it.x }.x
        val maxY = edges.maxBy { it.y }.y
        val maxX = edges.maxBy { it.x }.x

        val insideCoordinates = mutableSetOf<Coordinate>()
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val coord = Coordinate(x, y)
                if (coord in edges) continue

                if (isInsidePolygon(edges, coord)) {
                    insideCoordinates.add(coord)
                }
            }
        }
        edges.addAll(insideCoordinates)

        return edges.size - 1
    }

    private fun isInsidePolygon(polygon: List<Coordinate>, point: Coordinate): Boolean {
        var isInside = false
        var i = 0
        val n = polygon.size

        for (j in 1..n) {
            val p1 = polygon[i % n]
            val p2 = polygon[j % n]

            if (point.y > min(p1.y, p2.y) && point.y <= max(p1.y, p2.y) &&
                point.x <= max(p1.x, p2.x) && p1.y != p2.y &&
                point.x <= (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x
            ) {
                isInside = !isInside
            }
            i = j
        }
        return isInside
    }

    data class DigInstruction(val direction: Direction, val distance: Int, val edgeColorHex: String)

    data class Coordinate(val x: Int, val y: Int)

    enum class Direction(val symbol: String, val dX: Int, val dY: Int, val altSymbol: String) {
        Up("U", 0, -1, "3"),
        Down("D", 0, 1, "1"),
        Left("L", -1, 0, "2"),
        Right("R", 1, 0, "0");

        companion object {
            fun parse(candidate: String): Direction = entries.first { it.symbol == candidate }
            fun parseAlt(candidate:String): Direction = entries.first {it.altSymbol == candidate }
        }
    }

    override fun part2(inputString: String): Any {
        val hexColorRegex = Regex("#([a-z0-9]{5})(\\d)")
        val hexDigPlan = inputString.lines().map {
            val (distanceHexStr, directionStr) = hexColorRegex.find(it)!!.destructured
            val direction = Direction.parseAlt(directionStr)
            HexDigInstruction(direction, distanceHexStr.toLong(16))
        }

        // TODO: Fix part 2

        return hexDigPlan.size
    }

    data class HexDigInstruction(val direction: Direction, val distance: Long)
}