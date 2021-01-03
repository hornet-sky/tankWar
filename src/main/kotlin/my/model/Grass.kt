package my.model

import my.Config
import my.business.Coverable

class Grass(override var x: Int, override var y: Int): Coverable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun getImagePath() = "img/grass.gif"
}