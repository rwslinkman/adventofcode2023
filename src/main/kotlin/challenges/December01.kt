package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AnsiColor
import nl.rwslinkman.adventofcode2023.Day
import nl.rwslinkman.adventofcode2023.Main
import nl.rwslinkman.adventofcode2023.Main.paint

class December01: Day {

    override fun challenge1() {
        val inputString: String = Main.getResourceFileContent("december01/input.txt")

        val inputLines = inputString.lines()
        val sumResult = inputLines.sumOf {
            val includedNumbers = it.split("").mapNotNull { c -> c.toIntOrNull() }
            val lineNumber = "${includedNumbers.first()}${includedNumbers.last()}"
            lineNumber.toInt()
        }

        println("The ${"sum of the calibration values".paint(AnsiColor.Blue)} is ${sumResult.paint(AnsiColor.Red)} units")
    }

    override fun challenge2() {
        val numberWordMap: Map<String, Int> = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )
        val inputString: String = Main.getResourceFileContent("december01/input.txt")
        val inputLines = inputString.lines()
        val sumResult = inputLines.sumOf {
            var inputLine = it
            println(inputLine)
            numberWordMap.forEach { (word, num) ->


                var idx = 0
                while(idx >= 0) {
                    idx = inputLine.indexOf(word)
                    if (idx != -1) {
                        inputLine = inputLine.replaceRange(idx, idx+1, num.toString())
                    }
                }

//                inputLine = inputLine.replace(word, num.toString())
            }
            println(inputLine)
            val includedNumbers = inputLine.split("").mapNotNull { c -> c.toIntOrNull() }
            val lineNumber = "${includedNumbers.first()}${includedNumbers.last()}"
            println(lineNumber)
            println()
            lineNumber.toInt()
        }

        println("The improved ${"sum of the calibration values".paint(AnsiColor.Blue)} is ${sumResult.paint(AnsiColor.Red)} units")
    }
}
