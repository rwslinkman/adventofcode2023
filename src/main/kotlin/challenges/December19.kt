package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

typealias OperationFunction = (Long, Long) -> Boolean

@Puzzle("december19/input.txt")
object December19 : AdventChallenge {

    private const val ACCEPTED = "A"
    private const val REJECTED = "R"

    override fun part1(inputString: String): Any {
        val (workflows, partRatings) = parseWorkflowsAndParts(inputString)

        val acceptedParts = partRatings.filter { part ->
            val inWorkflow = workflows.first { it.name == "in" }
            var accepted = false
            val workflowQueue = mutableListOf(inWorkflow)

            while(workflowQueue.isNotEmpty()) {
                val currentWorkflow = workflowQueue.removeFirst()

                var isDone = false
                for(rule in currentWorkflow.rules ) {
                    val partValue = part[rule.partProp]!!
                    if(rule.operation.isTrue(partValue, rule.other)) {
                        when(rule.nextWorkflow) {
                            ACCEPTED -> {
                                accepted = true
                                workflowQueue.clear()
                            }
                            REJECTED -> {
                                workflowQueue.clear()
                            }
                            else -> {
                                // send to next workflow
                                val nextWorkflow = workflows.first { wf -> wf.name == rule.nextWorkflow }
                                workflowQueue.add(nextWorkflow)
                            }
                        }
                        isDone = true
                        break
                    }
                }

                if(!isDone) {
                    // No rule applied, use alternative
                    when (currentWorkflow.alternative) {
                        ACCEPTED -> {
                            accepted = true
                            workflowQueue.clear()
                        }

                        REJECTED -> {
                            workflowQueue.clear()
                        }

                        else -> {
                            // send to next workflow
                            val nextWorkflow = workflows.first { wf -> wf.name == currentWorkflow.alternative }
                            workflowQueue.add(nextWorkflow)
                        }
                    }
                }
            }
            accepted
        }

        return acceptedParts.sumOf {
            it.values.sum()
        }
    }

    private fun parseWorkflowsAndParts(inputString: String): Pair<List<Workflow>, List<Map<String, Long>>> {
        val lines = inputString.lines()
        val middleIndex = lines.indexOf("")
        // Workflows
        val workflowRegex = Regex("(\\S+)\\{([^{}]+)}")
        val ruleRegex = Regex("(\\S)([<>])(\\d+):(\\S+)")
        val workflows: List<Workflow> = lines.subList(0, middleIndex).map {
            val (workflowName, workflow) = workflowRegex.find(it)!!.destructured
            val workflowOperations = workflow.split(",").toMutableList()
            val workflowAlternative = workflowOperations.removeLast()
            val rules: List<Rule> = workflowOperations.map { wf ->
                val (partProp, opStr, otherStr, nextWorkFlow) = ruleRegex.find(wf)!!.destructured
                val operation = Operation.parse(opStr)
                Rule(partProp, operation, otherStr.toLong(), nextWorkFlow)
            }
            Workflow(workflowName, rules, workflowAlternative)

        }
        // Part ratings
        val partRatings: List<Map<String, Long>> = lines.subList(middleIndex + 1, lines.size).map { partLine ->
            val partValues = partLine.replace("}", "").replace("{", "")
            partValues.split(",").associate { partStr ->
                val (partName, partValue) = partStr.split("=")
                partName to partValue.toLong()
            }
        }
        return workflows to partRatings
    }

    data class Workflow(val name: String, val rules: List<Rule>, val alternative: String)

    data class Rule(val partProp: String, val operation: Operation, val other: Long, val nextWorkflow: String)

    enum class Operation(val symbol: String, val isTrue: OperationFunction) {
        GREATER(">", { a, b ->
            a > b
        }),
        LESS_THAN("<", { a, b ->
            a < b
        });
        companion object {
            fun parse(candidate: String): Operation = entries.first { it.symbol == candidate }
        }
    }

    data class Part(val propName: String, val partValue: Long)

    override fun part2(inputString: String): Any {
        val (workflows, _) = parseWorkflowsAndParts(inputString)

        val inWorkflow = workflows.first { it.name == "in" }

        val minMap = mutableMapOf(
            "x" to 4000L,
            "m" to 4000L,
            "a" to 4000L,
            "s" to 4000L,
        )
        val maxMap = mutableMapOf(
            "x" to 0L,
            "m" to 0L,
            "a" to 0L,
            "s" to 0L,
        )
        val queue = mutableListOf(Key(inWorkflow, minMap, maxMap))

        while(queue.isNotEmpty()) {
            val currentItem = queue.removeFirst()

            for (rule in currentItem.workflow.rules) {
                if (rule.operation == Operation.GREATER) {
                    minMap[rule.partProp] = minOf(minMap[rule.partProp]!!, rule.other + 1)
                } else {
                    maxMap[rule.partProp] = maxOf(maxMap[rule.partProp]!!, rule.other - 1)
                }

                when (rule.nextWorkflow) {
                    ACCEPTED -> {}
                    REJECTED -> {}
                    else -> {
                        val nextWorkflow = workflows.first { wf -> wf.name == rule.nextWorkflow }
                        queue.add(Key(nextWorkflow, minMap.toMutableMap(), maxMap.toMutableMap()))
                    }
                }
            }
        }


        val rangeX = (1..4000)
        val rangeM = (1..4000)
        val rangeA = (1..4000)
        val rangeS = (1..4000)

        return 0
    }


    data class Key(val workflow: Workflow, val minMap: Map<String, Long>, val maxMap: Map<String, Long>)
}