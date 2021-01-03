package my.model

import my.Config
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int): View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }
}