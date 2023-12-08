package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle
import java.util.LinkedList
import java.util.Queue

@Puzzle("december08/input.txt")
object December08: AdventChallenge {

    private const val LEFT = "L"

    override fun part1(inputString: String): Any {
        val (instructions, maps) = parseInput(inputString)

        var currentMap = maps.first { it.origin == "AAA" }
        var stepCounter = 0
        while(instructions.isNotEmpty()) {
            if (currentMap.origin == "ZZZ") {
                break
            }

            val instruction = instructions.poll()
            currentMap = if(instruction == LEFT) {
                maps.first { it.origin == currentMap.left }
            } else {
                maps.first { it.origin == currentMap.right }
            }
            stepCounter++
            instructions.add(instruction)
        }
        return stepCounter
    }

    private fun parseInput(inputString: String): Pair<Queue<String>, List<Map>> {
        val lines = inputString.lines().toMutableList()
        val leftRightInstructions: Queue<String> = queueOf(lines.removeFirst().split("").filter { it != "" }.toList())

        lines.removeFirst()
        val mapParserRegex = Regex("(\\S+) = \\((\\S+), (\\S+)\\)")
        val maps = lines.map {
            val (origin, left, right) = mapParserRegex.find(it)!!.destructured
            Map(origin, left, right)
        }
        return Pair(leftRightInstructions, maps)
    }

    data class Map(val origin: String, val left: String, val right: String)

    override fun part2(inputString: String): Any {
        val (instructions, maps) = parseInput(inputString)

        val startingNodes = maps.filter { it.origin.last() == 'A' }
        val results = startingNodes.map {
            var currentMap = it
            var stepCounter = 0L
            val itInstructions = queueOf(instructions.toList())
            while(itInstructions.isNotEmpty()) {
                if (currentMap.origin.last() == 'Z') {
                    break
                }

                val instruction = itInstructions.poll()
                currentMap = if(instruction == LEFT) {
                    maps.first { it.origin == currentMap.left }
                } else {
                    maps.first { it.origin == currentMap.right }
                }
                stepCounter++
                itInstructions.add(instruction)
            }
            stepCounter
        }
        return results.fold(1L) { acc, i -> lcm(acc, i) }
    }

    private fun <T> queueOf(items: Collection<T>): Queue<T> {
        val queue = LinkedList<T>()
        queue.addAll(items)
        return queue
    }

    private fun lcm(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}