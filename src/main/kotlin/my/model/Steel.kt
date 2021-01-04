package my.model

import my.Config
import my.business.Blockable
import my.business.Destroyable
import my.business.Sufferable

class Steel(override var x: Int, override var y: Int): Blockable, Sufferable, Destroyable {
    override var blood: Int = Config.defBlood * 999
    override val width: Int = Config.block
    override val height: Int = Config.block
    override fun isDestroy() = blood <= 0
    override fun getImagePath() = "img/steel.gif"
}