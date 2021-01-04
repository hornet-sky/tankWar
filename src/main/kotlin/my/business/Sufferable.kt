package my.business

import my.Config
import my.enums.Direction
import my.model.Blast
import my.model.Bullet
import my.model.Tank
import my.model.View
import org.itheima.kotlin.game.core.Composer

interface Sufferable: View {
    var blood:Int
    fun notifySuffer(target: Attackable): List<Blast>? {
        blood -= target.attackPower
        if(blood > 0) {
            val blast = when(target.currentDirection) {
                Direction.UP -> Blast(x, y + height / 2)
                Direction.RIGHT -> Blast(x - width / 2, y)
                Direction.DOWN -> Blast(x, y - height / 2)
                else -> Blast(x + width / 2, y)
            }
            if(this is Tank) Composer.play("snd/hit.wav")
            return listOf<Blast>(blast)
        } else if(blood <= 0) {
            if(this is Tank) Composer.play("snd/blast.wav")
            return listOf<Blast>(Blast(x, y))
        }
        return null
    }
}