package nl.rwslinkman.adventofcode2023.challenges

import nl.rwslinkman.adventofcode2023.AdventChallenge
import nl.rwslinkman.adventofcode2023.Puzzle

@Puzzle("december24/input.txt")
object December24 : AdventChallenge {
    override fun part1(inputString: String): Any {
        return countCollisions(inputString, RANGE_MIN, RANGE_MAX)
    }

    fun countCollisions(inputString: String, validRangeMin: Long, validRangeMax: Long): Any {
        val hailstones = parseHailstones(inputString)

        val hailstonePairs = mutableListOf<Pair<Hailstone, Hailstone>>()
        for (h in hailstones.indices) {
            val stone1 = hailstones[h]
            for (i in hailstones.indices) {
                if (i == h) continue
                val stone2 = hailstones[i]
                hailstonePairs.add(stone1 to stone2)
            }
        }
        val collisions = hailstonePairs.mapNotNull { pair ->
            findIntersection2(pair.first, pair.second)
        }
        val validCollisions = collisions.filter {
            it.x >= validRangeMin && it.x <= validRangeMax &&
            it.y >= validRangeMin && it.y <= validRangeMax
        }
        return validCollisions.count()
    }

    private fun findIntersection2(stone1: Hailstone, stone2: Hailstone): Vector2D? {
        val mx1 = stone1.m * stone1.posX
        val mx2 = stone2.m * stone2.posX
        val dY = stone2.posY - stone1.posY
        val dm = stone1.m - stone2.m

        val intersectionX = (mx1 - mx2 - dY) / dm
        val y = stone1.m * (intersectionX - stone1.posX) + stone1.posY

        val time1 = (intersectionX - stone1.posX) / stone1.velocityX
        val time2 = (intersectionX - stone2.posX) / stone2.velocityX

        val isInFuture = time1 > 0 && time2 > 0
        return if(isInFuture) Vector2D(intersectionX, y) else null
    }

//    private fun findIntersection(stone1: Hailstone, stone2: Hailstone): Vector2D? {
//        val relativePosX = stone2.posX - stone1.posX
//        val relativePosY = stone2.posY - stone1.posY
//        val relativeVx = stone2.velocityX - stone1.velocityX
//        val relativeVy = stone2.velocityY - stone1.velocityY
//
//        if (relativeVx == 0 || relativeVy == 0) {
//            return if (relativePosX == 0L && relativePosY == 0L) {
//                Vector2D(stone1.posX, stone1.posY)
//            } else null
//        }
//
//        return if (relativePosX / relativeVx == relativePosY / relativeVy) {
//            // Calculate collision time (when they'll intersect) using the ratio of positions to velocities
//            val collisionTime = relativePosX / relativeVx
//
//            // Calculate the collision coordinates based on stone1's position and velocity at the collision time
//            val collisionX = stone1.posX + stone1.velocityX * collisionTime
//            val collisionY = stone1.posY + stone1.velocityY * collisionTime
//
//            Vector2D(collisionX, collisionY)
//        } else null
//    }

    private const val RANGE_MIN = 200000000000000L
    private const val RANGE_MAX = 400000000000000

    private fun parseHailstones(inputString: String): List<Hailstone> {
        val hailstoneRegex = Regex("([-\\d]+)[, ]+([-\\d]+)[, ]+([-\\d]+)[,@ ]+([-\\d]+)[, ]+([-\\d]+)[, ]+([-\\d]+)")
        val hailstones = inputString.lines().map {
            val (px, py, pz, vx, vy, vz) = hailstoneRegex.find(it)!!.destructured
            val m = vy.toDouble() / vx.toDouble()
            Hailstone(px.toLong(), py.toLong(), pz.toLong(), vx.toInt(), vy.toInt(), vz.toInt(), m)
        }
        return hailstones
    }

    data class Hailstone(
        val posX: Long,
        val posY: Long,
        val posZ: Long,
        val velocityX: Int,
        val velocityY: Int,
        val velocityZ: Int,
        val m: Double,
    )

    data class Vector2D(val x: Double, val y: Double)

    override fun part2(inputString: String): Any {
        return 0
    }
}