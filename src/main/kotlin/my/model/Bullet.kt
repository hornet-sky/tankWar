package my.model

import my.Config
import my.business.*
import my.enums.Direction
import org.itheima.kotlin.game.core.Painter

class Bullet(var owner: Attackable): AutoMovable, Destroyable, Sufferable {
    companion object {
        val SIZE_MAP = mapOf<Direction, Pair<Int, Int>>(
            Direction.UP to Painter.size("img/bullet_d.gif").let { Pair(it[0], it[1])},
            Direction.RIGHT to Painter.size("img/bullet_l.gif").let { Pair(it[0], it[1])},
            Direction.DOWN to Painter.size("img/bullet_u.gif").let { Pair(it[0], it[1])},
            Direction.LEFT to Painter.size("img/bullet_r.gif").let { Pair(it[0], it[1])},
        )

    }
    override var speed: Int = Config.defSpeed * 2
    override val allowOutOfMapBound: Boolean = true
    override var blockDirection: Direction? = null
    override var blockTarget: Blockable? = null
    override var currentDirection: Direction = owner.currentDirection
    override val width: Int
    override val height: Int
    override var blood: Int = Config.defBlood

    override var x: Int = 0
    override var y: Int = 0
    init {
        SIZE_MAP.get(currentDirection).let {
            width = it!!.first
            height = it!!.second
        }
        when(currentDirection) {
            Direction.UP -> {
                x = owner.x + owner.width / 2 - width / 2
                y = owner.y - height / 2
            }
            Direction.RIGHT -> {
                x = owner.x + owner.width - width / 2
                y = owner.y + owner.height / 2 - height / 2
            }
            Direction.DOWN -> {
                x = owner.x + owner.width / 2 - width / 2
                y = owner.y + owner.height - height / 2
            }
            Direction.LEFT -> {
                x = owner.x - width / 2
                y = owner.y + owner.height / 2 - height / 2
            }
        }
    }

    override fun isDestroy(): Boolean {
        return isOutOfBounds() || blood <= 0
    }

    override fun getImagePath(): String {
        return when(currentDirection) {
            Direction.UP -> "img/bullet_d.gif"
            Direction.RIGHT -> "img/bullet_l.gif"
            Direction.DOWN -> "img/bullet_u.gif"
            else -> "img/bullet_r.gif"
        }
    }
}