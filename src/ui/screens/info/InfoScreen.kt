package ui.screens.info

import tornadofx.*
import ui.screens.TruthTableViewModel
import ui.screens.truthtable.TruthTableScreen
import uitls.ExpressionValidate
import kotlin.math.pow

class InfoScreen : View("Calculator") {
    private val model: TruthTableViewModel by inject()

    override val root = vbox {
        paddingAll = 10

        label(model.expression) { isWrapText = true }

        hbox {
            label("●") { isWrapText = true }
            label("Количество переменных: ") {
                paddingLeft = 5
                isWrapText = true
            }
            label(model.args) {
                isWrapText = true
            }
        }

        hbox {
            label("●") { isWrapText = true }
            label("Количество строк в таблице: ") {
                paddingLeft = 5
                isWrapText = true
            }
            label(model.rows) {
                isWrapText = true
            }
        }
        hbox {
            label("●") { isWrapText = true }
            label("Количество операций: ") {
                paddingLeft = 5
                isWrapText = true
            }
            label(model.operations) {
                isWrapText = true
            }
        }

        hbox {
            label("●") { isWrapText = true }
            label("Количество столбцов в таблице: ") {
                paddingLeft = 5
                isWrapText = true
            }
            label(model.columns) {
                isWrapText = true
            }
        }

        button("Построить таблицу") {
            useMaxWidth = true
            action { replaceWith(TruthTableScreen::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT)) }
        }
    }
}