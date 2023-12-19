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

        val start = cityMap.first { it.x == 0 && it.y == 0 }
        val maxX = cityMap.maxBy { it.x }.x
        val end = cityMap.filter { it.x == maxX }.maxBy { it.y }

        val options = mutableListOf(
            Route(0, 0, Direction.East, start),
        )
        val visited = mutableListOf<String>()
        while (options.isNotEmpty()) {
            val currentRoute = options.removeFirst()

            if (currentRoute.city == end) {
                return currentRoute.heatLost
            }

            if (currentRoute.key() in visited) {
                continue
            }
            visited.add(currentRoute.key())

            for (dir in Direction.entries) {
                val nextStep = if (dir == currentRoute.direction) currentRoute.steps + 1 else 1
                if (nextStep > MAX_STEPS_WITHOUT_TURN) {
                    continue
                }

                val nextX = currentRoute.city.x + dir.dX
                val nextY = currentRoute.city.y + dir.dY
                val nextCity = cityMap.find {
                    it.x == nextX && it.y == nextY
                } ?: continue
                if(nextCity == currentRoute.city) continue

                val nextRoute = nextCity.let {
                    Route(currentRoute.heatLost + it.heat, nextStep, dir, it)
                }
                if (nextRoute.key() !in visited) {
                    options.add(nextRoute)
                }
            }
        }
        return 0
    }

    private const val MAX_STEPS_WITHOUT_TURN = 3

    data class City(val x: Int, val y: Int, val heat: Int)

    data class Route(val heatLost: Int, val steps: Int, val direction: Direction, val city: City)

    private fun Route.key(): String = "${city.x}_${city.y}_${direction.dX}_${direction.dY}_${steps}"

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