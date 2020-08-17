package ui.screens.input


import tornadofx.Controller
import tornadofx.ViewTransition
import tornadofx.seconds
import ui.screens.TruthTableViewModel
import ui.screens.info.InfoScreen
import uitls.ExpressionValidate
import uitls.isValid
import kotlin.math.pow

class InputController : Controller() {
    private val model : TruthTableViewModel by inject()

    fun setExpression(expression: String) {
        if (isValid(expression, model)) {

            val symbolsCounter = mutableListOf<Char>()
            var operationsCounter = 0

            model.expression.value.forEach { symbol ->
                if (symbol in 'A'..'Z' && !symbolsCounter.contains(symbol))
                    symbolsCounter.add(symbol)
                if (symbol in ExpressionValidate.basis || symbol == '¬')
                    operationsCounter++
            }

            model.args.value = symbolsCounter.size.toString()
            model.rows.value = 2.0.pow(symbolsCounter.size).toInt().toString()
            model.operations.value = operationsCounter.toString()
            model.columns.value = (symbolsCounter.size + operationsCounter).toString()

            find(InputScreen::class).replaceWith(InfoScreen::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
        }
    }
}