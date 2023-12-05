package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december05/input.txt")
object December05 : AdventChallenge {
    override fun part1(inputString: String): Any {
        val (initialSeeds, mappingsList) = parseAlmanac(inputString)

        val locationValues = initialSeeds.map { initialSeed ->
            var current = initialSeed
            mappingsList.forEach { mapping ->
                val foundRange = mapping.ranges.find {
                    it.sourceRangeStart <= current && current < (it.sourceRangeStart + it.rangeLength)
                }
                foundRange?.let { r ->
                    current = current - r.sourceRangeStart + r.destRangeStart
                }
            }
            current
        }
        return locationValues.min()
    }

    private fun parseAlmanac(inputString: String): Pair<List<Long>, List<CategoryMapping>> {
        val initialSeeds = mutableListOf<Long>()
        val seedsFinderRegex = Regex("seeds:\\s+(\\d+\\s+\\d+(?:\\s+\\d+)*)")
        val newMappingFinderRegex = Regex("(\\w+)-to-(\\w+) map:")
        val rangeFinderRegex = Regex("(\\d+) (\\d+) (\\d+)")

        val mappingsList: MutableList<CategoryMapping> = mutableListOf()
        var activeMapping: CategoryMapping? = null
        inputString.lines().forEach { line ->
            if (line.isEmpty()) return@forEach

            val foundSeeds = seedsFinderRegex.find(line)?.groupValues?.last() ?: ""
            if (foundSeeds.isNotEmpty()) {
                val foundSeedNumbers = foundSeeds.split(" ").mapNotNull { it.toLongOrNull() }
                initialSeeds.addAll(foundSeedNumbers)
                return@forEach
            }

            val foundMapping = newMappingFinderRegex.find(line)
            foundMapping?.let {
                activeMapping?.let { am -> mappingsList.add(am) }
                val (sourceCategory, destCategory) = it.destructured
                activeMapping = CategoryMapping(sourceCategory, destCategory, mutableListOf())
                return@forEach
            }

            val foundRange = rangeFinderRegex.find(line)
            foundRange?.let {
                val (destStartRange, sourceStartRange, rangeLength) = it.destructured
                activeMapping?.ranges?.add(
                    MapRange(
                        destStartRange.toLong(),
                        sourceStartRange.toLong(),
                        rangeLength.toLong()
                    )
                )
            }
        }
        activeMapping?.let { am -> mappingsList.add(am) }
        return Pair(initialSeeds, mappingsList)
    }

    data class CategoryMapping(val source: String, val destination: String, val ranges: MutableList<MapRange>)
    data class MapRange(val destRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long)

    override fun part2(inputString: String): Any {
        val (initialSeeds, mappingsList) = parseAlmanac(inputString)
        var locationMin = Long.MAX_VALUE

        initialSeeds.windowed(2, 2).forEach {
            val seedRangeStart = it[0]
            val seedRangeLength = it[1]

            for (i in (seedRangeStart..<seedRangeStart + seedRangeLength)) {
                locationMin = findInRange(i, locationMin, mappingsList)
            }
        }
        return locationMin
    }

    private fun findInRange(seed: Long, locationMin: Long, mappingsList: List<CategoryMapping>): Long {
        var current = seed
        mappingsList.forEach { mapping ->
            val foundRange = mapping.ranges.find {
                it.sourceRangeStart <= current && current < (it.sourceRangeStart + it.rangeLength)
            }
            foundRange?.let { r ->
                current = current - r.sourceRangeStart + r.destRangeStart
            }
        }
        return minOf(current, locationMin)
    }
}