package my.model

import my.Config
import org.itheima.kotlin.game.core.Painter

class Wall(override var x: Int, override var y: Int): View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun getImagePath() = "img/wall.gif"
}