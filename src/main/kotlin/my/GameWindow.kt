package my

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import my.business.*
import my.enums.Direction
import my.model.*
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow: Window(title = "坦克大战", icon = "img/tank_u.gif", width = Config.gameWindowWidth, height = Config.gameWindowHeight) {
    private val views = CopyOnWriteArrayList<View>(); // 线程安全的List，允许多个线程同时操作该List实例
    private lateinit var tank: Tank
    private lateinit var camp: Camp
    private var gameOver:Boolean = false
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
                        'D' -> views.add(EnemyTank(colIdx * Config.block, rowIdx * Config.block, Direction.DOWN))
                    }
                }
            }
        }

        tank = Tank(10 * Config.block, 12 * Config.block, Direction.UP)
        views.add(tank)

        camp = Camp(Config.gameWindowWidth / 2 - Config.block, Config.gameWindowHeight - Config.block - Config.block / 2)
        views.add(camp)

        Composer.play("snd/start.wav")
    }

    override fun onDisplay() {
        views.filter {
            if(it !is Coverable) {
                it.draw()
            }
            it is Coverable
        }.forEach { // 覆盖层最后画
            it.draw()
        }
        if(gameOver) {
            Painter.drawImage("img/gameover.gif", Config.gameWindowWidth / 2 - 48, Config.gameWindowHeight - 96)
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        if(gameOver) return
        when(event.code) {
            KeyCode.W, KeyCode.UP -> tank.notifyMove(Direction.UP)
            KeyCode.D, KeyCode.RIGHT -> tank.notifyMove(Direction.RIGHT)
            KeyCode.S, KeyCode.DOWN -> tank.notifyMove(Direction.DOWN)
            KeyCode.A, KeyCode.LEFT -> tank.notifyMove(Direction.LEFT)
            KeyCode.SPACE, KeyCode.ENTER -> {
                tank.shot()?.let {
                    views.add(it) // 添加射出的子弹
                }
            }
        }
    }

    override fun onRefresh() {
        val blockableList: List<Blockable> = views.filter { it is Blockable }.map { it as Blockable }
        val sufferableList: List<Sufferable> = views.filter { it is Sufferable }.map { it as Sufferable }
        views.forEach { v ->
            if(v is Destroyable) {
                v as Destroyable
                if(v.isDestroy()) {
                    views.remove(v)
                    if(v is Camp) {
                        gameOver = true
                    }
                    return@forEach // 结束当前函数体，类似于 continue
                }
            }
            if(gameOver) {
                return
            }
            if(v is Movable) {
                v as Movable
                var blockDirection: Direction? = null
                var blockTarget: Blockable? = null
                blockableList.forEach loop1@ { target ->
                    if(v != target) {
                        if(v is Bullet) {
                            v as Bullet
                            if(v.owner == target // 自己与自己发射的子弹不相互阻塞
                                || target is Water) { // 子弹能穿过水
                                return@loop1
                            }
                        }
                        v.willCollision(target)?.let { // 找到了阻塞物
                            blockDirection = it
                            blockTarget = target
                            return@loop1
                        }

                    }
                }
                v.notifyCollision(blockDirection, blockTarget)
            }
            if(v is AutoMovable) {
                if(v is AutoRandomMovable) v as AutoRandomMovable
                else v as AutoMovable
                v.notifyAutoMove()
                if(v is Bullet) {
                    v as Bullet
                    sufferableList.forEach {
                        if(v != it && v.owner != it &&  v.checkSeamless(it)) {
                            views.remove(v)
                            it.notifySuffer(v.owner)?.let {
                                views.addAll(it)
                            }
                        }
                    }
                }
            }
            if(v is AutoAttackable) {
                v as AutoAttackable
                v.autoShot()?.let {
                    views.add(it)
                }
            }
        }
    }
}