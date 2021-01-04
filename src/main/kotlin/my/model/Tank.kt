package my.model

import my.Config
import my.business.*
import my.enums.Direction
import org.itheima.kotlin.game.core.Composer
import kotlin.random.Random

open class Tank(override var x: Int, override var y: Int, override var currentDirection: Direction): Movable, Blockable, Attackable, Destroyable, Sufferable {
    override var blood: Int = Config.defBlood * 2
    override val width: Int = Config.block
    override val height: Int = Config.block
    override var cd: Long = 0
    override var attackPower: Int = Config.defAttackPower
    override var speed: Int = Config.defSpeed
    override val allowOutOfMapBound: Boolean = false
    override var blockDirection: Direction? = null
    override var blockTarget: Blockable? = null

    override fun shot(): Bullet? {
        if(cd == null || cd == 0L) {
            cd = System.currentTimeMillis() + 500
            return null;
        }
        var now = System.currentTimeMillis()
        if(now > cd) {
            cd = now + 500
            Composer.play("snd/fire.wav")
            return Bullet(this)
        }
        return null
    }

    override fun isDestroy() = blood <= 0

    override fun getImagePath(): String {
        return when(currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.RIGHT -> "img/tank_r.gif"
            Direction.DOWN -> "img/tank_d.gif"
            else -> "img/tank_l.gif"
        }
    }

}