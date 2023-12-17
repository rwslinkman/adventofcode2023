package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december13/input.txt")
object December13 : AdventChallenge {
    override fun part1(inputString: String): Any {
        val patterns = extractPatterns(inputString)

        val summary = patterns.map { rows ->
            // Check Horizontal
            val mirrorIndexHorizontal = findMirrorIndex(rows)
            // Check vertical
            val columns = getColumns(rows)
            val mirrorIndexVertical = findMirrorIndex(columns)

            Pair(mirrorIndexVertical, mirrorIndexHorizontal)
        }

        return summary.sumOf {
            it.first + it.second * 100L
        }
    }

    private fun findMirrorIndex(pattern: List<String>): Int {
        for (i in 1..<pattern.size) {
            val span = minOf(i, pattern.size - i)

            val topHalf = pattern.subList(i - span, i)
            val bottomHalf = pattern.subList(i, i + span).reversed()

            if (topHalf.size != bottomHalf.size) continue

            var listEqual = true
            for (l in topHalf.indices) {
                listEqual = listEqual && (topHalf[l] == bottomHalf[l])
            }

            if (listEqual) return i
        }
        return 0
    }

    private fun getColumns(it: List<String>): MutableList<String> {
        val columns = mutableListOf<String>()
        for (i in it[0].indices) {
            val lineColums = it.map { line -> line[i] }.joinToString("")
            columns.add(lineColums)
        }
        return columns
    }

    private fun extractPatterns(inputString: String): List<List<String>> {
        val patterns = mutableListOf<List<String>>()
        var pattern = mutableListOf<String>()
        for (line in inputString.lines()) {
            if (line.isEmpty()) {
                patterns.add(pattern.toList())
                pattern = mutableListOf()
            } else {
                pattern.add(line)
            }
        }
        patterns.add(pattern.toList())
        return patterns.toList()
    }

    override fun part2(inputString: String): Any {
        val patterns = extractPatterns(inputString)

        val summary = patterns.map { rows ->
            // Check Horizontal
            val mirrorIndexHorizontal = findMirrorIndexWithSmudges(rows)
            // Check vertical
            val columns = getColumns(rows)
            val mirrorIndexVertical = findMirrorIndexWithSmudges(columns)

            Pair(mirrorIndexVertical, mirrorIndexHorizontal)
        }

        return summary.sumOf {
            it.first + it.second * 100L
        }
    }

    private fun findMirrorIndexWithSmudges(pattern: List<String>): Int {
        for (i in 1..<pattern.size) {
            val span = minOf(i, pattern.size - i)

            val topHalf = pattern.subList(i - span, i)
            val bottomHalf = pattern.subList(i, i + span).reversed()

            if (topHalf.size != bottomHalf.size) continue

            val misMatches = topHalf.zip(bottomHalf).sumOf {
                it.first.zip(it.second).count { charPair -> charPair.first != charPair.second }
            }
            if(misMatches == 1) {
                return i
            }
        }
        return 0
    }
}