package my.model

import my.Config
import my.business.Moveable
import my.enums.Direction
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int): Moveable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    override var currentDirection: Direction = Direction.UP
    override var speed: Int = Config.defSpeed

    override fun getImagePath(): String {
        return when(currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.RIGHT -> "img/tank_r.gif"
            Direction.DOWN -> "img/tank_d.gif"
            else -> "img/tank_l.gif"
        }
    }
}