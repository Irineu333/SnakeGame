import view.Game
import view.MainWindow

fun main() {
    val game = Game()

    val window = MainWindow("SnakeNeo")

    window.add(game)
    window.mostrar()

    game.start()
}