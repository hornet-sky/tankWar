package my.business

import my.enums.Direction
import my.model.View

interface Moveable: View {
    var currentDirection: Direction
    var speed: Int
    fun willCollision(target: View): Direction? {
        val xIntersect = !(x + width < target.x || x > target.x + target.width)
        val yIntersect = !(y + height < target.y || y > target.y + target.height)
        if(currentDirection == Direction.UP && y - speed < target.y + target.height && xIntersect) {
            return Direction.UP
        }
        if(currentDirection == Direction.DOWN && y + height + speed > target.y && xIntersect) {
            return Direction.DOWN
        }
        if(currentDirection == Direction.LEFT && x - speed < target.x + target.width && yIntersect) {
            return Direction.LEFT
        }
        if(currentDirection == Direction.RIGHT && x + width + speed > target.x && yIntersect) {
            return Direction.RIGHT
        }
        return null
    }

    fun notifyMove(direction: Direction) {
        if(direction != currentDirection) {
            currentDirection = direction
            return
        }
        when(currentDirection) {
            Direction.UP -> y -= speed
            Direction.RIGHT -> x += speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
        }
    }
}