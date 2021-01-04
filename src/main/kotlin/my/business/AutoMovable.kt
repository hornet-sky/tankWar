package my.business

import my.Config
import my.enums.Direction
import my.model.View

interface AutoMovable: Movable {
    fun notifyAutoMove() {
        notifyMove(currentDirection)
    }
}