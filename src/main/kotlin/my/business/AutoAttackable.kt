package my.business

import my.Config
import my.enums.Direction
import my.model.Bullet
import my.model.View
import org.itheima.kotlin.game.core.Composer
import kotlin.random.Random

interface AutoAttackable: Attackable {
    fun autoShot(): Bullet? {
        if(cd == null || cd == 0L) {
            cd = System.currentTimeMillis() + Random.nextInt(3000)
            return null;
        }
        var now = System.currentTimeMillis()
        if(now > cd) {
            cd = now + Random.nextInt(3000)
            Composer.play("snd/fire.wav")
            return Bullet(this)
        }
        return null
    }
}