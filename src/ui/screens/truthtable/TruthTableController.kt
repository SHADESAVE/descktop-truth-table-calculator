package ui.screens.truthtable

import tornadofx.Controller
import ui.screens.TruthTableViewModel

class TruthTableController : Controller() {
    //    val model = TruthTableViewModel()
    private var expression: String = ""
    private var currentSymbol: Char? = null
    private var position: Int = -1
    private val strList = mutableListOf<String>()

    private fun createTruthTable() {
        nextSymbol()
        val result = equivalence()
        println("Result string: $result")
        println("Result list: $strList")
    }

    private fun equivalence(): String {
        var string = "" + implication()
        while (true) {
            when {
                check('↔') -> {
                    string += '↔'
                    string += implication()
                    strList.add(string)
                }
                else -> return string
            }
        }
    }

    private fun implication(): String {
        var string = "" + disjunction()
        while (true) {
            when {
                check('→') -> {
                    string += '→'
                    //string += currentSymbol
                    string += disjunction()
                    strList.add(string)
                }
                else -> return string
            }
        }
    }

    private fun disjunction(): String {
        var string = "" + conjunction()
        while (true) {
            when {
                check('∨') -> {
                    string += '∨'
                    string += conjunction()
                    strList.add(string)
                }
                else -> return string
            }
        }
    }

    private fun conjunction(): String {
        var string = "" + inversion()
        while (true) {
            when {
                check('∧') -> {
                    string += '∧'
                    string += inversion()
                    strList.add(string)
                }
                else -> return string
            }
        }
    }

    private fun inversion(): String {
        var string = "" + brackets()
        while (true) {
            when {
                check('¬') -> {
                    string += '¬'
                    string += brackets()
                    strList.add(string)
                }
                else -> return string
            }
        }
    }

    private fun brackets(): String {
        var string = ""
        while (true) {
            when {
                check('(') -> {
                    string += '('
                    string += equivalence()

                    if (check(')')) {
                        string += ')'
                    } else println("ERROR: Отсутствует закрывающая скобка: $position")
                }
                else -> {
                    if (currentSymbol in 'A'..'Z') {
                        string += currentSymbol
                        nextSymbol()
                    }
                    return string
                }
            }
        }
    }

    /*
    Legacy
    private fun inversion(): String {
        var string = ""

        if (check('(')) {
            string += '('
            string += equivalence()

            if (check(')')) {
                string += ')'
            } else println("ERROR: Отсутствует закрывающая скобка: $position")

        } else {
            if (check('¬')) {
                string += '¬'
                string += inversion()
                strList.add(string)
            } else {
                if (currentSymbol in 'A'..'Z') {
                    string += currentSymbol
                    nextSymbol()
                }
            }
        }
        return string
    }

    */

    private fun check(checkingSymbol: Char): Boolean {
        if (currentSymbol == checkingSymbol) {
            nextSymbol()
            return true
        }
        return false
    }

    private fun nextSymbol() {
        if (++position < expression.length)
            currentSymbol = expression[position]
        else println("Последний символ: $currentSymbol")
    }

    fun setExpression(expression: String) {
        this.expression = expression
        strList.clear()
        currentSymbol = null
        position = -1
        createTruthTable()
    }
}