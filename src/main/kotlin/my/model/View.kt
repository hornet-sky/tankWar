package my.model

import org.itheima.kotlin.game.core.Painter

interface View {
    var x:Int
    var y:Int

    val width:Int
    val height:Int

    fun draw() {
        Painter.drawImage(getImagePath(), x, y)
    }

    fun getImagePath(): String

    fun checkCollision(target: View): Boolean {
        return !(x + width < target.x || x > target.x + target.width
                || y + height < target.y || y > target.y + target.height)
    }
}