package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december17/input.txt")
object December17 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val cityMap = inputString.lines().flatMapIndexed { y, line ->
            line.mapIndexed { x, heatChar ->
                City(x, y, heatChar.digitToInt())
            }
        }

        val startCity = cityMap.first { it.x == 0 && it.y == 0 }
        val maxX = cityMap.maxBy { it.x }.x
        val endCity = cityMap.filter { it.x == maxX }.maxBy { it.y }

        val startNode = Node(startCity, 1, Direction.East)
        val startNode2 = Node(startCity, 1, Direction.South)
        val options = mutableListOf(
            Element(0, startNode),
            Element(0, startNode2)
        )
        val visited = mutableSetOf<Node>()
        while (options.isNotEmpty()) {
            val currentElement = options.removeFirst()

            if (currentElement.node.city == endCity) {
                return currentElement.heatLost
            }

            if(currentElement.node in visited) continue

            for (dir in Direction.entries) {
                val nextStep = if (dir == currentElement.node.direction) currentElement.node.steps + 1 else 1
                if (nextStep > MAX_STEPS_WITHOUT_TURN) {
                    continue
                }

                val nextX = currentElement.node.city.x + dir.dX
                val nextY = currentElement.node.city.y + dir.dY
                val nextCity = cityMap.find {
                    it.x == nextX && it.y == nextY
                } ?: continue

                val nextNode = Node(nextCity, nextStep, dir)
                if(nextNode in visited) continue

                val nextElement = Element(currentElement.heatLost + nextNode.city.heat, nextNode)
                if(nextElement in options) continue
                options.add(nextElement)
            }
        }
        return 0
    }

    private const val MAX_STEPS_WITHOUT_TURN = 3

    data class City(val x: Int, val y: Int, val heat: Int)

    data class Node(val city: City, val steps: Int, val direction: Direction)

    data class Element(val heatLost: Int, val node: Node)

//    data class Route(val heatLost: Int, val steps: Int, val direction: Direction, val city: City)

//    private fun Route.key(): String = "${city.x}_${city.y}_${direction.dX}_${direction.dY}_${steps}"

    enum class Direction(val dX: Int, val dY: Int) {
        North(0, -1),
        East(1, 0),
        South(0, 1),
        West(-1, 0)
    }

    override fun part2(inputString: String): Any {
        return 0
    }
}