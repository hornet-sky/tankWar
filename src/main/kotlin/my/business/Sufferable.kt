package my.business

import my.Config
import my.enums.Direction
import my.model.Blast
import my.model.Bullet
import my.model.View

interface Sufferable: View {
    var blood:Int
    fun notifySuffer(target: Attackable): List<View>? {
        if(--blood > 0) {
            val blast = when(target.currentDirection) {
                Direction.UP -> Blast(x, y + height / 2)
                Direction.RIGHT -> Blast(x - width / 2, y)
                Direction.DOWN -> Blast(x, y - height / 2)
                else -> Blast(x + width / 2, y)
            }
            return listOf<Blast>(blast)
        } else if(blood == 0) {
            return listOf<Blast>(Blast(x + width / 2, y + height / 2))
        }
        return null
    }
}