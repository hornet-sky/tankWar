import javafx.application.Application
import javafx.scene.input.KeyEvent
import my.Config
import org.itheima.kotlin.game.core.Window

class MyWindow: Window(title = "坦克大战", icon = "img/tank_u.gif", width = Config.gameWindowWidth, height = Config.gameWindowHeight) {
    override fun onCreate() {
        println("onCreate")
    }

    override fun onDisplay() {
        //println("onDisplay")
    }

    override fun onKeyPressed(event: KeyEvent) {
        println("onKeyPressed [ keyCode = ${event.code} ]")
    }

    override fun onRefresh() {
        //println("onRefresh")
    }
}

fun main(args: Array<String>) {
    Application.launch(MyWindow::class.java)
}