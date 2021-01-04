package my.business

import my.Config
import my.enums.Direction
import my.model.Bullet
import my.model.View

interface Attackable: View {
    var cd:Long
    var attackPower:Int
    var currentDirection: Direction
    fun shot(): Bullet?
}