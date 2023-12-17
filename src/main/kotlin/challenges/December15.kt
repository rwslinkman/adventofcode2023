package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december15/input.txt")
object December15 : AdventChallenge {

    override fun part1(inputString: String): Any {
        val parts = inputString.split(",")

        return parts.sumOf { part ->
            reindeerHash(part)
        }
    }

    private fun reindeerHash(part: String): Long {
        return part.map { it.code }.fold(0L) { currentValue, digit ->
            // The algorithm
            ((currentValue + digit) * 17) % 256
        }
    }

    override fun part2(inputString: String): Any {
        val parts = inputString.split(",")
        val boxes = MutableList(256) {
            mutableMapOf<String, Int>()
        }

        parts.forEach {
            if (it.contains("=")) {
                val itemParts = it.split("=")
                val label = itemParts[0]
                val boxNumber = reindeerHash(label).toInt()
                boxes[boxNumber][label] = itemParts[1].toInt()
            } else {
                val key = it.replace("-", "")
                val boxNumber = reindeerHash(key).toInt()
                boxes[boxNumber].remove(key)
            }
        }

        val allFocusingPowers = boxes.flatMapIndexed { box, lenses ->
            lenses.entries.mapIndexed { slot, lens ->
                // Calculation for focusing power
                (1 + box) * (slot + 1) * lens.value
            }
        }
        return allFocusingPowers.sum()
    }
}