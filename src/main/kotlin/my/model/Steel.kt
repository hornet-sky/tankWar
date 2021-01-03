package my.model

import my.Config
import my.business.Blockable

class Steel(override var x: Int, override var y: Int): Blockable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun getImagePath() = "img/steel.gif"
}