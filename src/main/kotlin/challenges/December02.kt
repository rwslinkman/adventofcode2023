package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december02/input.txt")
object December02 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val maxRed = 12
        val maxGreen = 13
        val maxBlue = 14

        val games = parseInputToCubeGames(inputString)

        val possibleGames = games.filter {
            val redMax = it.cubeSets.maxOf { set -> set["red"] ?: 0 }
            val greenMax = it.cubeSets.maxOf { set -> set["green"] ?: 0 }
            val blueMax = it.cubeSets.maxOf { set -> set["blue"] ?: 0 }
            redMax <= maxRed && greenMax <= maxGreen && blueMax <= maxBlue
        }
        return possibleGames.sumOf { it.gameId }
    }

    override fun part2(inputString: String): Any {
        val games = parseInputToCubeGames(inputString)

        return games.sumOf {
            val redMax = it.cubeSets.maxOf { set -> set["red"] ?: 0 }
            val greenMax = it.cubeSets.maxOf { set -> set["green"] ?: 0 }
            val blueMax = it.cubeSets.maxOf { set -> set["blue"] ?: 0 }
            redMax * greenMax * blueMax
        }
    }

    private fun parseInputToCubeGames(inputString: String): List<CubeGame> {
        val gameNumberRegex = Regex("Game (\\d*): ")
        val cubeSetRegex = Regex("(\\d+) (\\w*)")
        val games = inputString
            .lines()
            .map {
                val match = gameNumberRegex.find(it)
                val (gameNumber) = match!!.destructured
                val cubeSetsLine = it.split(": ").last()
                val cubeSet = cubeSetsLine
                    .split("; ")
                    .map { cubeSetPart ->
                        cubeSetRegex
                            .findAll(cubeSetPart)
                            .map { match -> match.groupValues[2] to match.groupValues[1].toInt() }
                            .toMap()
                    }
                CubeGame(gameNumber.toInt(), cubeSet)
            }
        return games
    }

    data class CubeGame(val gameId: Int, val cubeSets: List<Map<String, Int>>)
}