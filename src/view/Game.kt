package view

import model.Apple
import model.Direction
import model.Node
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import kotlin.random.Random

class Game : Canvas(), Runnable, KeyListener {

    private val altura = 410
    private val largura = 480

    private val random = Random((0..maxOf(largura, altura)).shuffled().first())

    private val nodeSnake = MutableList(1) { Node(0, 13) }
    private val directions = Direction()
    private var speed = 1f

    var score = 0
    private val apple = Apple(random.nextInt(largura - 10), random.nextInt(13, altura - 10))

    init {
        preferredSize = Dimension(largura, altura)
        addKeyListener(this)
    }

    override fun run() {
        while (true) {
            tick()
            render()
            Thread.sleep(1000 / 60) //60fps
        }
    }

    private fun render() {
        if (bufferStrategy == null) {
            createBufferStrategy(3)
            return
        }

        //draw

        bufferStrategy.drawGraphics.let { graphics ->

            //background
            graphics.color = Color.BLACK
            graphics.fillRect(0, 0, largura, altura)

            //nodes
            nodeSnake.forEach { node ->
                graphics.color = Color.BLUE
                graphics.fillRect(node.x, node.y, 10, 10)
            }

            //apple
            graphics.color = Color.RED
            graphics.fillRect(apple.x, apple.y, 10, 10)

            //score background
            graphics.color = Color.GRAY
            graphics.fillRect(0, 0, largura, 13)

            //score
            graphics.color = Color.WHITE
            graphics.drawString("Pontos: $score | Speed ${speed.toInt()}", 0, 10)

            graphics.dispose()
        }
        bufferStrategy.show()
    }

    private fun tick() {

        //todos os nodes seguem o primeiro
        for (index in nodeSnake.size.dec() downTo 1) {
            nodeSnake[index].x = nodeSnake[index-1].x
            nodeSnake[index].y = nodeSnake[index-1].y
        }

        //infinito x
        if(nodeSnake[0].x + 10 < 0) {
            nodeSnake[0].x = largura
        } else if(nodeSnake[0].x > largura) {
            nodeSnake[0].x = 0
        }

        //infinito y
        if(nodeSnake[0].y < 0) {
            nodeSnake[0].y = altura
        } else if(nodeSnake[0].y > altura) {
            nodeSnake[0].y = 13
        }

        //direções
        when(directions.direction) {
            Direction.Directions.RIGHT -> nodeSnake[0].x += speed.toInt()
            Direction.Directions.LEFT -> nodeSnake[0].x -= speed.toInt()
            Direction.Directions.DOWN -> nodeSnake[0].y += speed.toInt()
            Direction.Directions.UP -> nodeSnake[0].y -= speed.toInt()
        }

        //intersecção

        val node = Rectangle(nodeSnake[0].x, nodeSnake[0].y, 10, 10)
        val apple = Rectangle(this.apple.x, this.apple.y, 10, 10)

        if(node.intersects(apple)) {
            this.apple.x = random.nextInt(largura-10)
            this.apple.y = random.nextInt(13, altura-10)

            //add nodes
            val x = nodeSnake[nodeSnake.size-1].x
            val y = nodeSnake[nodeSnake.size-1].y
            nodeSnake.addAll(Array(10) { Node(x, y) })

            score++
            speed += .25f
            println("Pontos: $score")
        }

    }

    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(event: KeyEvent?) {
        when (event?.keyCode) {

            KeyEvent.VK_RIGHT -> directions.right()
            KeyEvent.VK_LEFT -> directions.left()
            KeyEvent.VK_UP -> directions.up()
            KeyEvent.VK_DOWN -> directions.down()
        }
    }

    override fun keyReleased(e: KeyEvent?) {

    }

    fun start() {
        Thread(this).start()
    }


}