package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december20/input.txt")
object December20 : AdventChallenge {
    override fun part1(inputString: String): Any {
        val data = parseConnectors(inputString)
        val broadcaster = data.first { it.name == "broadcaster" }

        repeat(1000) {
            // Simulate pulse
            val remainingPulses = mutableListOf(
                Pulse(OtherModule("button"), false, broadcaster)
            )
            while (remainingPulses.isNotEmpty()) {
                val currentPulse = remainingPulses.removeFirst()

                val nextPulses: List<Pulse> = if (currentPulse.isHighPulse) {
                    currentPulse.target.highPulse(data, currentPulse.sender.name)
                } else {
                    currentPulse.target.lowPulse(data, currentPulse.sender.name)
                }
                remainingPulses.addAll(nextPulses)
            }
        }

        var totalLowPulses = 1000L // button presses
        var totalHighPulses = 0L
        data.forEach {
            totalLowPulses += it.lowPulseSentCount
            totalHighPulses += it.highPulseSentCount
        }
        return totalHighPulses * totalLowPulses
    }

    private fun parseConnectors(inputString: String, addDeadEnds: Boolean = false): List<Module> {
        val lineRegex = Regex("(\\S+) -> (.+)")
        val data = inputString.lines().map {
            val (origin, targetStr) = lineRegex.find(it)!!.destructured
            val targets = targetStr.split(", ")

            val name = origin.substring(1)
            when (origin.first()) {
                FLIPFLOP -> FlipFlop(name, targets)
                CONJUNCTION -> Conjunction(name, targets)
                'o' -> DebugModule(origin)
                else -> OtherModule(origin, targets)
            }
        }.toMutableList()
        data.filterIsInstance<Conjunction>().forEach {
            val sourceModules = data.filter { mod -> it.name in mod.targetNames }
            it.setSourceModules(sourceModules)
        }
        if(addDeadEnds) {
            val existingNames = data.map { it.name }
            val deadEnds = mutableListOf<Module>()
            data.forEach {
                val missingModules = it.targetNames
                    .filter { name -> name !in existingNames }
                    .map { missingName -> OtherModule(missingName) }
                deadEnds.addAll(missingModules)
            }
            data.addAll(deadEnds)
        }
        return data.toList()
    }

    private const val FLIPFLOP = '%'
    private const val CONJUNCTION = '&'

    // TODO: rx is not counted because its not a module in 'targets:List<Module>'
    data class Pulse(val sender: Module, val isHighPulse: Boolean, val target: Module)

    sealed class Module(val name: String, val targetNames: List<String>) {

        var lowPulseSentCount = 0
        var highPulseSentCount = 0

        abstract fun highPulse(targets: List<Module>, sender: String): List<Pulse>
        abstract fun lowPulse(targets: List<Module>, sender: String): List<Pulse>
    }

    class FlipFlop(name: String, targets: List<String>) : Module(name, targets) {
        private var isOn: Boolean = false

        override fun highPulse(targets: List<Module>, sender: String): List<Pulse> {
            // It is ignored & nothing happens
            return listOf()
        }

        override fun lowPulse(targets: List<Module>, sender: String): List<Pulse> {
            isOn = !isOn
            val myTargets = targets.filter { it.name in targetNames }
            return if (isOn) {
                // It turns on: send high pulse to targets
                highPulseSentCount += targetNames.size
                myTargets.map { Pulse(this, true, it) }
            } else {
                // It turns off: send low pulses to targets
                lowPulseSentCount += targetNames.size
                myTargets.map { Pulse(this, false, it) }
            }
        }

    }

    class Conjunction(name: String, targets: List<String>) : Module(name, targets) {
        private lateinit var receivedPulses: MutableMap<String, Boolean>

        override fun highPulse(targets: List<Module>, sender: String): List<Pulse> {
            receivedPulses[sender] = true
            return sendPulses(targets)
        }

        override fun lowPulse(targets: List<Module>, sender: String): List<Pulse> {
            receivedPulses[sender] = false
            return sendPulses(targets)
        }

        fun setSourceModules(sources: List<Module>) {
            receivedPulses = sources.associate { it.name to false }.toMutableMap()
        }

        private fun sendPulses(targets: List<Module>): List<Pulse> {
            val myTargets = targets.filter { it.name in targetNames }
            val allPulsesHigh = receivedPulses.values.all { it }
            if (allPulsesHigh) {
                lowPulseSentCount += targetNames.size
            } else {
                highPulseSentCount += targetNames.size
            }
            return myTargets.map { Pulse(this, !allPulsesHigh, it) }
        }
    }

    class OtherModule(name: String, targetNames: List<String> = listOf()) : Module(name, targetNames) {
        override fun highPulse(targets: List<Module>, sender: String): List<Pulse> {
            // Not used
            return listOf()
        }

        override fun lowPulse(targets: List<Module>, sender: String): List<Pulse> {
            val myTargets = targets.filter { it.name in targetNames }
            lowPulseSentCount += targetNames.size
            return myTargets.map { Pulse(this, false, it) }
        }
    }

    class DebugModule(name: String) : Module(name, listOf()) {
        override fun highPulse(targets: List<Module>, sender: String): List<Pulse> = listOf()
        override fun lowPulse(targets: List<Module>, sender: String): List<Pulse> = listOf()
    }

    override fun part2(inputString: String): Any {
        val data = parseConnectors(inputString, addDeadEnds = true)
        val broadcaster = data.first { it.name == "broadcaster" }

        var buttonPresses = 0
        var wasRxFound = false
        while(!wasRxFound) {
            buttonPresses++

            // Simulate pulse
            val remainingPulses = mutableListOf(
                Pulse(OtherModule("button"), false, broadcaster)
            )
            while (remainingPulses.isNotEmpty()) {
                val currentPulse = remainingPulses.removeFirst()

                if(currentPulse.target.name == TARGET_MODULE) {
                    val hilo = if(currentPulse.isHighPulse) "high" else "low"
                    println("Sending pulse to $TARGET_MODULE => $hilo")
                }

                val nextPulses: List<Pulse> = if (currentPulse.isHighPulse) {
                    currentPulse.target.highPulse(data, currentPulse.sender.name)
                } else {
                    currentPulse.target.lowPulse(data, currentPulse.sender.name)
                }

                val containsRX = nextPulses.find { it.target.name == TARGET_MODULE }
                if(containsRX != null && !containsRX.isHighPulse) {
                    wasRxFound = true
                    remainingPulses.clear()
                } else {
                    remainingPulses.addAll(nextPulses)
                }
            }
        }

        return buttonPresses + 1
    }

    private const val TARGET_MODULE = "rx"
}
