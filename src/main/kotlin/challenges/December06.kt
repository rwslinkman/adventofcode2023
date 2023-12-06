package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december06/input.txt")
object December06 : AdventChallenge {
    override fun part1(inputString: String): Any {
        val numberFinder = Regex("(\\d+)")
        val lines = inputString.lines()
        val numbersTime = numberFinder.findAll(lines[0]).map { it.groupValues[1].toInt() }.toList()
        val numbersDist = numberFinder.findAll(lines[1]).map { it.groupValues[1].toInt() }.toList()
        val racesList = numbersTime.zip(numbersDist).map { BoatRace(it.first, it.second) }

        return racesList.map {
            var winOptionsCounter = 0
            for (t in 1..<it.raceDuration) {
                val moveTime = it.raceDuration - t
                val dist = moveTime * t
                if (dist >= it.recordDistance) {
                    winOptionsCounter++
                }
            }
            winOptionsCounter
        }.reduce { acc, item ->
            acc * item
        }
    }

    class BoatRace(val raceDuration: Int, val recordDistance: Int)

    override fun part2(inputString: String): Any {
        val lines = inputString.lines()
        val raceDuration = lines[0].replace("Time:", "").replace(" ", "").toLong()
        val recordDistance = lines[1].replace("Distance:", "").replace(" ", "").toLong()

        var winOptionsCounter = 0
        for (t in 1..<raceDuration) {
            val moveTime = raceDuration - t
            val dist = moveTime * t
            if (dist >= recordDistance) {
                winOptionsCounter++
            }
        }
        return winOptionsCounter
    }
}