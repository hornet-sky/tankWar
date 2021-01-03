package my

import javafx.scene.input.KeyEvent
import my.model.*
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow: Window(title = "坦克大战", icon = "img/tank_u.gif", width = Config.gameWindowWidth, height = Config.gameWindowHeight) {
    private val views = CopyOnWriteArrayList<View>(); // 线程安全的List，允许多个线程同时操作该List实例
    override fun onCreate() {
        BufferedReader(InputStreamReader(javaClass.getResourceAsStream("/map/1.map"), "utf-8")).use {
            it.readLines().forEachIndexed { rowIdx, line ->
                line.toCharArray().forEachIndexed { colIdx, ch ->
                    when(ch) {
                        // Z砖 T铁 C草 S水 D敌方坦克出生点
                        'Z' -> views.add(Wall(colIdx * Config.block, rowIdx * Config.block))
                        'T' -> views.add(Steel(colIdx * Config.block, rowIdx * Config.block))
                        'C' -> views.add(Grass(colIdx * Config.block, rowIdx * Config.block))
                        'S' -> views.add(Water(colIdx * Config.block, rowIdx * Config.block))
                    }
                }
            }
        }
    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
    }

    override fun onRefresh() {
    }
}