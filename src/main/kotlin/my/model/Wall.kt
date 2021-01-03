package my.model

import my.Config
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int): View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }
}