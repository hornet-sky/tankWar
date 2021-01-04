package my.business

import my.Config
import my.enums.Direction
import my.model.Bullet
import my.model.View

interface Consumable: View {
    var consumeCount:Int
    fun notifyConsume();
}