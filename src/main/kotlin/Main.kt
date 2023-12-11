package nl.rwslinkman.adventofcode2023

import nl.rwslinkman.adventofcode2023.challenges.*
import kotlin.reflect.full.findAnnotations

object Main {

    private val adventChallenges: List<AdventChallenge> = listOf(
        December01,
        December02,
        December03,
        December04,
        December05,
        December06,
        December07,
        December08,
        December09,
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val dayChallenge = adventChallenges.last()
        val puzzle: Puzzle = dayChallenge::class.findAnnotations<Puzzle>().firstOrNull()
            ?: throw RuntimeException("Expected object to implement Puzzle annotation")
        val dayName = dayChallenge::class.simpleName!!

        println()
        println("---------------- Advent Of Code 2023 -------------------".paint(AnsiColor.Blue))
        println("------------------ by Rick Slinkman --------------------".paint(AnsiColor.Red))
        println("---- https://github.com/rwslinkman/adventofcode2023 ----".paint(AnsiColor.Yellow))
        println()
        println("Running puzzles of ${dayName.paint(AnsiColor.Red)}")

        val inputString: String = getResourceFileContent(puzzle.inputFileName)
        val resultPart1 = dayChallenge.part1(inputString)
        println("The result of ${"part 1".paint(AnsiColor.Cyan)} is ${resultPart1.paint(AnsiColor.Green)}")

        val resultPart2 = dayChallenge.part2(inputString)
        println("The result of ${"part 2".paint(AnsiColor.Purple)} is ${resultPart2.paint(AnsiColor.Green)}")
    }

    // helper functions below
    private fun getResourceFileContent(fileName: String): String =
        Main.javaClass.classLoader.getResource(fileName)?.readText() ?: ""

    private fun Any.paint(color: AnsiColor = AnsiColor.Default) = this.paint(color.colorCode)
    private fun Any.paint(color: String) = "${color}${this}${AnsiColor.Default.colorCode}"
}
