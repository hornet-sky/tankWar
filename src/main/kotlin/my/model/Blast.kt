package my.model

import my.Config
import my.business.Blockable
import my.business.Destroyable

class Blast(override var x: Int, override var y: Int): Destroyable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    var statusIdx: Int = 0

    override fun isDestroy(): Boolean {
        return statusIdx >= 32
    }
    override fun getImagePath(): String {
        if(++statusIdx > 32) {
            statusIdx = 32
        }
        return "img/blast_${statusIdx}.png"
    }
}