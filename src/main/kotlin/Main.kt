package nl.rwslinkman.adventofcode2023

import nl.rwslinkman.adventofcode2023.challenges.*

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val days: List<Day> = listOf(
            December01,
            December02
        )
        val dayChallenge = days.last()

        dayChallenge.challenge1()
        dayChallenge.challenge2()
    }

    // helper functions below
    fun getResourceFileContent(fileName: String): String = Main.javaClass.classLoader.getResource(fileName)?.readText() ?: ""

    fun Any.paint(color: AnsiColor = AnsiColor.Default) = this.paint(color.colorCode)
    fun Any.paint(color: String) = "${color}${this}${AnsiColor.Default.colorCode}"
}

