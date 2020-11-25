import view.Game
import view.MainWindow

/**
 * Inicialização da Aplicação
 * @author Irineu A. Silva
 */

fun main(args: Array<String>) {
    val game = Game()

    val window = MainWindow("SnakeNeo")

    window.add(game)
    window.mostrar()

    game.start()
}