package uitls

import javafx.stage.StageStyle
import tornadofx.find
import ui.fragments.PopupFragment
import ui.screens.TruthTableViewModel
import ui.screens.input.InputScreen
import uitls.ExpressionValidate.basis

object ExpressionValidate {
    val basis = listOf('∧', '∨', '→', '↔')
}

fun isValid(expression: String, model: TruthTableViewModel): Boolean {

    var openBracket = 0
    var closeBracket = 0

    if (expression[0] !in 'A'..'Z' && expression[0] != '¬' && expression[0] != '(') {
        model.errorMsg.value = "На позиции ${1} обнаружен неожидаемый символ: ${expression[0]}"
        showErrorFragment()
        return false
    }

    for (i in 0 until expression.length - 1) {
        when (expression[i]) {
            in 'A'..'Z' -> {
                if (expression[i + 1] !in basis && expression[i + 1] != ')') {
                    model.errorMsg.value = "На позиции ${i + 2} обнаружен неожидаемый символ: ${expression[i + 1]}"
                    showErrorFragment()
                    return false
                }
            }
            in basis -> {
                if (expression[i + 1] !in 'A'..'Z' && expression[i + 1] != '(' && expression[i + 1] != '¬') {
                    model.errorMsg.value = "На позиции ${i + 2} обнаружен неожидаемый символ: ${expression[i + 1]}"
                    showErrorFragment()
                    return false
                }
            }
            '¬' -> {
                if (expression[i + 1] !in 'A'..'Z' && expression[i + 1] != '(' && expression[i + 1] != '¬') {
                    model.errorMsg.value = "На позиции ${i + 2} обнаружен неожидаемый символ: ${expression[i + 1]}"
                    showErrorFragment()
                    return false
                }
            }
            '(' -> {
                if (expression[i + 1] !in 'A'..'Z' && expression[i + 1] != '¬' && expression[i + 1] != '(') {
                    model.errorMsg.value = "На позиции ${i + 2} обнаружен неожидаемый символ: ${expression[i + 1]}"
                    showErrorFragment()
                    return false
                }
                openBracket++
            }
            ')' -> {
                closeBracket++
                if (closeBracket > openBracket) {
                    model.errorMsg.value = "Ошибка в количестве скобок: открывающих $openBracket, закрывающих $closeBracket"
                    showErrorFragment()
                    return false
                }
                if (expression[i + 1] !in basis && expression[i + 1] != ')') {
                    model.errorMsg.value = "На позиции ${i + 2} обнаружен неожидаемый символ: ${expression[i + 1]}"
                    showErrorFragment()
                    return false
                }
            }
            else -> {
                showErrorFragment()
                return false
            }
        }
    }

    when (expression[expression.lastIndex]) {
        ')' -> {
            closeBracket++
        }
        in 'A'..'Z' -> {
        }
        else -> {
            model.errorMsg.value = "на позиции ${expression.lastIndex + 1} обнаружен неожидаемый символ: ${expression[expression.lastIndex]}"
            showErrorFragment()
            return false
        }
    }

    return if (openBracket > closeBracket || closeBracket > openBracket) {
        model.errorMsg.value = "Ошибка в количестве скобок: открывающих $openBracket, закрывающих $closeBracket"
        showErrorFragment()
        false
    } else {
        true
    }
}

private fun showErrorFragment() {
    find<InputScreen>().openInternalWindow<PopupFragment>()
}