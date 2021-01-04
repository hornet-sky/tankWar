package my.model

import my.Config
import my.business.Attackable
import my.business.Blockable
import my.business.Destroyable
import my.business.Sufferable
import my.enums.Direction
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

class Camp(override var x: Int, override var y: Int): Blockable, Sufferable, Destroyable {
    override var blood: Int = Config.defBlood * 7
    override var width: Int = 2 * Config.block
    override var height: Int = Config.block  + Config.block / 2
    override fun isDestroy() = blood <= 0
    override fun getImagePath() = "img/symbol.gif"
    override fun draw() {

        if(blood > 1) {
            drawWall(if (blood > 4) "img/steel_small.gif" else "img/wall_small.gif")
            Painter.drawImage(getImagePath(), x + Config.block / 2, y + Config.block / 2)
        } else if(blood == 1) { // 失去围墙后调整宽高及坐标点
            width = Config.block
            height = Config.block
            x = Config.gameWindowWidth / 2 - Config.block / 2
            y = Config.gameWindowHeight - Config.block
            Painter.drawImage(getImagePath(), x, y)
        }
        println("blood = ${blood}, width = ${width}, height = ${height}, x = ${x}, y = ${y}")
    }
    fun drawWall(imagePath: String) {
        Painter.drawImage(imagePath, x, y)
        Painter.drawImage(imagePath, x, y + Config.block / 2)
        Painter.drawImage(imagePath, x, y + Config.block)
        Painter.drawImage(imagePath, x + Config.block / 2, y)
        Painter.drawImage(imagePath, x + Config.block, y)
        Painter.drawImage(imagePath, x + Config.block + Config.block / 2, y)
        Painter.drawImage(imagePath, x + Config.block + Config.block / 2, y + Config.block / 2)
        Painter.drawImage(imagePath, x + Config.block + Config.block / 2, y + Config.block)
    }
    override fun notifySuffer(target: Attackable): List<Blast>? {
        blood -= target.attackPower
        if(blood > 0) {
            val blast = when(target.currentDirection) {
                Direction.UP -> Blast(target.x, y + height / 2)
                Direction.RIGHT -> Blast(x - width / 2, target.y)
                Direction.DOWN -> Blast(target.x, y - height / 2)
                else -> Blast(x + width / 2, target.y)
            }
            var offset = Config.block / 4
            return when(blood) {
                4, 1 -> listOf<Blast>(
                    Blast(x - offset, y - offset),
                    Blast(x - offset, y - offset + Config.block / 2),
                    Blast(x - offset, y - offset + Config.block),
                    Blast(x - offset + Config.block / 2, y - offset),
                    Blast(x - offset + Config.block, y - offset),
                    Blast(x - offset + Config.block + Config.block / 2, y - offset),
                    Blast(x - offset + Config.block + Config.block / 2, y - offset + Config.block / 2),
                    Blast(x - offset + Config.block + Config.block / 2, y - offset + Config.block)
                )
                else -> listOf<Blast>(blast)
            }
        } else if(blood <= 0) {
            Composer.play("snd/blast.wav")
            return listOf<Blast>(Blast(x, y))
        }
        return null
    }
}