package my.business

import my.enums.Direction
import java.util.*

interface AutoRandomMovable: AutoMovable {
    override fun notifyAutoMove() {
        blockDirection?.let {
            var ran = Random()
            var randDirection: Direction? = null
            do {
                randDirection = when(ran.nextInt(4)) {
                    0 -> Direction.UP
                    1 -> Direction.RIGHT
                    2 -> Direction.DOWN
                    else -> Direction.LEFT
                }
            } while (randDirection == it)
            currentDirection = randDirection!!
        }
        super.notifyAutoMove()
    }
}