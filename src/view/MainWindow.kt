package view

import javax.swing.JFrame

/**
 * Janela principal, onde o jogo é exibido
 * @author Irineu A. Silva
 */

class MainWindow(titulo : String) : JFrame(titulo) {
    init {
        isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    fun mostrar() {
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}