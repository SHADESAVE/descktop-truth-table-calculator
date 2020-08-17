package ui.screens.truthtable

import tornadofx.*
import ui.screens.TruthTableViewModel
import ui.screens.input.InputScreen

class TruthTableScreen : View("Calculator") {
    private val model: TruthTableViewModel by inject()
    private val controller: TruthTableController by inject()

    override val root = vbox {
        paddingAll = 10

        label(model.expression)

        button("Back") {
            action {
                replaceWith(InputScreen::class)
            }
        }
    }

    override fun onDock() {
        controller.setExpression(model.expression.value)
    }
}