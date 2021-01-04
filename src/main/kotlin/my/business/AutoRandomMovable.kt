package my.business

import my.enums.Direction
import kotlin.random.Random

interface AutoRandomMovable: AutoMovable {
    override fun notifyAutoMove() {
        if(blockDirection != null) {
            var randDirection: Direction? = null
            do {
                randDirection = getRandomDirection()
            } while (randDirection == blockDirection)
            currentDirection = randDirection!!
        } else if(isBlockOfBounds()) {
            var randDirection: Direction? = null
            do {
                randDirection = getRandomDirection()
            } while (randDirection == currentDirection)
            currentDirection = randDirection!!
        }
        super.notifyAutoMove()
    }

    private fun getRandomDirection(): Direction {
        return when(Random.nextInt(4)) {
            0 -> Direction.UP
            1 -> Direction.RIGHT
            2 -> Direction.DOWN
            else -> Direction.LEFT
        }
    }
}