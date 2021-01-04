package my.model

import my.Config
import my.business.*
import my.enums.Direction
import org.itheima.kotlin.game.core.Composer

class EnemyTank(x: Int, y: Int, currentDirection: Direction): Tank(x, y, currentDirection), AutoRandomMovable, AutoAttackable {
    override var speed: Int = 2
    override var cd: Long = 0L

    override fun getImagePath(): String {
        return when(currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            else -> "img/enemy_1_l.gif"
        }
    }

}