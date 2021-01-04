package my.business

import my.Config
import my.enums.Direction
import my.model.View

interface Movable: View {
    var currentDirection: Direction
    var speed: Int
    val allowOutOfMapBound: Boolean
    var blockDirection: Direction?
    var blockTarget: Blockable?
    fun willCollision(target: Blockable): Direction? {
        return if(currentDirection == Direction.UP && checkCollision(x, y - speed, target)) Direction.UP
        else if(currentDirection == Direction.DOWN && checkCollision(x, y + speed, target)) Direction.DOWN
        else if(currentDirection == Direction.LEFT && checkCollision(x - speed, y, target)) Direction.LEFT
        else if(currentDirection == Direction.RIGHT && checkCollision(x + speed, y, target)) Direction.RIGHT
        else null
    }

    fun outOfBounds(): Direction? {
        return when {
            y < 0 -> Direction.UP
            y > Config.gameWindowHeight -> Direction.DOWN
            x < 0 -> Direction.LEFT
            x > Config.gameWindowWidth -> Direction.RIGHT
            else -> null
        }
    }

    fun isOutOfBounds(): Boolean {
        return outOfBounds() != null
    }

    fun notifyCollision(blockDirection: Direction?, target: Blockable?) {
        this.blockDirection = blockDirection
        this.blockTarget = target
    }

    fun notifyMove(direction: Direction) {
        if(direction != currentDirection) {
            currentDirection = direction // 转向
            return
        }
        if(currentDirection == blockDirection) {
            // 贴近阻塞物
            blockTarget?.let {
                when(blockDirection) {
                    Direction.UP -> y = it.y + it.height
                    Direction.RIGHT -> x = it.x - width
                    Direction.DOWN -> y = it.y - height
                    Direction.LEFT -> x = it.x + it.width
                }
            }
            return
        }
        // 朝指定方向移动
        when(currentDirection) {
            Direction.UP -> {
                y -= speed
                if (!allowOutOfMapBound && y < 0) y = 0
            }
            Direction.RIGHT -> {
                x += speed
                if (!allowOutOfMapBound && x + width > Config.gameWindowWidth) x = Config.gameWindowWidth - width
            }
            Direction.DOWN -> {
                y += speed
                if (!allowOutOfMapBound && y + height > Config.gameWindowHeight) y = Config.gameWindowHeight - height
            }
            Direction.LEFT -> {
                x -= speed
                if (!allowOutOfMapBound && x < 0) x = 0
            }
        }
    }
}