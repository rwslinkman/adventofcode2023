package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december12/input.txt")
object December12 : AdventChallenge {

    private const val OPERATIONAL = '.'
    private const val DAMAGED = '#'
    private const val UNKNOWN = '?'

    private val cache = mutableMapOf<String, Long>()

    override fun part1(inputString: String): Any {
        val data = inputString.lines().map { line ->
            val splitLine = line.split(" ")
            val springCondition = splitLine.first()
            val groups = splitLine.last().split(",").map { it.toInt() }.toList()
            Pair(springCondition, groups)
        }

        cache.clear()
        val arrangementSums = data.map {
            getPossibleArrangements(it.first, it.second)
        }
        return arrangementSums.sum()
    }

    private fun getPossibleArrangements(pattern: String, groups: List<Int>): Long {
        val cacheKey = "${groups.joinToString(",")};${pattern}"
        if (cache.containsKey(cacheKey)) {
            return cache[cacheKey]!!
        }

        val space = pattern.length

        if(groups.size == 1) {
            val opts = MutableList(space - groups[0] + 1) {
                OPERATIONAL.repeat(it) + DAMAGED.repeat(groups[0]) + OPERATIONAL.repeat(space - it - groups[0])
            }
            val lastResult = opts.count { isMatching(it, pattern) }
            cache[cacheKey] = lastResult.toLong()
            return lastResult.toLong()
        }

        val first = groups[0]
        val rest = groups.takeLast(groups.size - 1)
        val restMinSpace = rest.sum() + rest.size - 1
        val firstPossiblePos = space - restMinSpace - first

        var arrangements = 0L
        for (i in 0..<firstPossiblePos) {
            val current = OPERATIONAL.repeat(i) + DAMAGED.repeat(first) + OPERATIONAL

            if(!isMatching(current, pattern.substring(0, current.length))) {
                continue
            }
            arrangements += getPossibleArrangements(pattern.substring(current.length), rest)
        }
        cache[cacheKey] = arrangements
        return arrangements
    }

    private fun isMatching(opt: String, pattern: String): Boolean {
        if(opt.length != pattern.length) {
            return false
        }

        for(i in opt.indices) {
            if (pattern[i] == UNKNOWN) {
                continue
            } else if (opt[i] != pattern[i]) {
                return false
            }
        }
        return true
    }

    override fun part2(inputString: String): Any {
        val data = inputString.lines().map { line ->
            val splitLine = line.split(" ")
            val hotSpringCondition = splitLine.first()
            val unfoldedSpringCondition = MutableList(5) { it }.joinToString(UNKNOWN.toString()) { hotSpringCondition }
            val groups = splitLine.last().split(",").map { it.toInt() }.toList()
            val unfoldedGroups = MutableList(5) { it } .flatMap { groups }
            Pair(unfoldedSpringCondition, unfoldedGroups)
        }

        cache.clear()
        val arrangementSums = data.map {
            getPossibleArrangements(it.first, it.second)
        }
        return arrangementSums.sum()
    }

    private fun Char.repeat(times: Int): String {
        var result = ""
        repeat(times) {
            result += this
        }
        return result
    }
}