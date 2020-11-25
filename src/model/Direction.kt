package model

class Direction {
    var direction: Directions? = null

    fun left() {
        direction = Directions.LEFT
    }

    fun right() {
        direction = Directions.RIGHT
    }

    fun up() {
        direction = Directions.UP
    }

    fun down() {
        direction = Directions.DOWN
    }

    enum class Directions {
        LEFT, RIGHT, UP, DOWN
    }
}