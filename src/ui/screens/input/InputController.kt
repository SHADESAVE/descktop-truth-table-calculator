package ui.screens.input


import tornadofx.Controller
import tornadofx.ViewTransition
import tornadofx.seconds
import ui.screens.TruthTableViewModel
import ui.screens.basis
import ui.screens.info.InfoScreen
import uitls.ExpressionValidate
import uitls.isValid
import javax.script.ScriptEngineManager
import kotlin.math.pow


class InputController : Controller() {
    private val model: TruthTableViewModel by inject()
    private val strList = mutableListOf<String>()

    // Для преобразования строки в булеан выражение
    private val mgr = ScriptEngineManager()
    private val engine = mgr.getEngineByName("JavaScript")

    private var expression: String = ""
    private var currentSymbol: Char? = null
    private var position: Int = -1

    fun setExpression(expression: String) {
        if (isValid(expression, model)) {

            this.expression = expression
            strList.clear()
            currentSymbol = null
            position = -1

            val args = mutableListOf<String>()
            var operationsCounter = 0

            model.expression.value.forEach { symbol ->
                if (symbol in 'A'..'Z' && !args.contains(symbol.toString()))
                    args.add(symbol.toString())
                if (symbol in ExpressionValidate.basis || symbol == '¬')
                    operationsCounter++
            }

            model.argsCount.value = args.size.toString()
            model.operationsCount.value = operationsCounter.toString()
            model.rowsCount.value = 2.0.pow(args.size).toInt().toString()
            model.columnsCount.value = (args.size + operationsCounter).toString()
            model.tableColumns.value.addAll(args)

            getTruthTable()

            find(InputScreen::class).replaceWith(InfoScreen::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
        }
    }

    private fun getTruthTable() {
        nextSymbol()
        val result = equivalence()

        println("Full string: $result")
        println("Result list: $strList")

        model.tableColumns.value.addAll(strList)
        println("Columns: ${model.tableColumns.value}")

        fill()
    }

    private fun fill() {
        val argsCount = model.argsCount.value.toInt()
        val rows = model.rowsCount.value.toInt()
        val columns = model.columnsCount.value.toInt()
        val operationsList = mutableListOf<String>()
        val operationsResultList = mutableListOf<List<Boolean>>()

        val truthTable = createTable(
                rows = rows,
                argsCount = argsCount
        )

        for (i in 0 until columns - argsCount) {
            operationsList.add(
                    model.tableColumns.value[i + argsCount]
                            .replace(basis[0].toString(), "==")
                            .replace(basis[1].toString(), "<=")
                            .replace(basis[2].toString(), "||")
                            .replace(basis[3].toString(), "&&")
                            .replace(basis[4].toString(), "!")
            )
        }

        // Переменные в промежуточных действиях (Например, А, B, C и т.д) заменяются соответствующими переменными из truthTable,
        for (row in 0 until rows) {
            val tempList = mutableListOf<Boolean>()

            for (operation in 0 until operationsList.size) {
                var currentOperation = operationsList[operation]

                for (arg in 0 until argsCount)
                    currentOperation = Regex("""\b${model.tableColumns.value[arg]}""").replace(
                            input = currentOperation,
                            replacement = truthTable[row][arg].toString())

                // Вычисление строкового булеанового выражения и добавление во вспомогательный лист
                tempList.add(java.lang.Boolean.valueOf(engine.eval(currentOperation) as Boolean))
            }
            operationsResultList.add(tempList)
        }


        val truthTableBoolean = mutableListOf<List<Boolean>>()
        val truthTableString = mutableListOf<List<String>>()

        for (row in 0 until rows)
            truthTableBoolean.add(listOf(truthTable[row].toList(), operationsResultList[row]).flatten())

        truthTableBoolean.forEach { innerList ->
            val tempL = mutableListOf<String>()

            innerList.forEach {
                if (it) tempL.add("1")
                else tempL.add("0")
            }
            truthTableString.add(tempL)
        }

        model.tableRows.value.setAll(truthTableString)

        var counter = 0
        truthTableString.forEach { innerList -> counter += innerList[innerList.size-1].toInt() }
        when (counter) {
            truthTableString.size -> model.formulasResult.value = "Формула является тождественно истинной"
            0 -> model.formulasResult.value = "Формула является тождественно ложной (невыполнимой)"
            else -> model.formulasResult.value = "Формула является выполнимой"
        }
    }

    private fun createTable(rows: Int, argsCount: Int): Array<Array<Boolean>> {
        val truthTable = Array(rows) {
            Array(argsCount) { false }
        }

        for (column in 0 until argsCount) {
            val toggle = 2.0.pow(argsCount - column - 1).toInt()
            var state = true

            for (row in 0 until rows) {
                if (row % toggle == 0)
                    state = !state

                truthTable[row][column] = state
            }
        }

        return truthTable
    }

    private fun equivalence(): String {
        var string = "" + implication()
        while (true) {
            when {
                check(basis[0]) -> {
                    string += basis[0]
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
                check(basis[1]) -> {
                    string += basis[1]
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
                check(basis[2]) -> {
                    string += basis[2]
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
                check(basis[3]) -> {
                    string += basis[3]
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
                check(basis[4]) -> {
                    string += basis[4]
                    string += inversion()
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
}