package ui.screens.truthtable

import javafx.beans.property.ReadOnlyStringWrapper
import tornadofx.*
import ui.screens.TruthTableViewModel
import ui.screens.input.InputScreen

class TruthTableScreen : View("Calculator") {
    private val model: TruthTableViewModel by inject()

    override val root = vbox {
        setPrefSize(600.0, 800.0)
        paddingAll = 10

        label(model.expression)

        tableview(model.tableRows) {
            for (i in 0 until model.tableColumns.value.size) {
                column<List<String>, String>(title = model.tableColumns.value[i]) {
                    ReadOnlyStringWrapper(it.value[i])
                }
            }
        }

        label(model.formulasResult)


        button("Назад") {
            useMaxWidth = true
            action {
                replaceWith(InputScreen::class)
            }
        }
    }

    override fun onUndock() {
        FX.getComponents().remove(TruthTableScreen::class)
    }
}