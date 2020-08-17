package ui.screens.input

import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import tornadofx.*
import ui.screens.TruthTableViewModel

class InputScreen : View("Calculator") {
    private val controller: InputController by inject()
    private val model: TruthTableViewModel by inject()

    override val root = vbox {
        paddingAll = 10

        textfield(model.expression) {
            isEditable = false
            style { fontSize = 20.px }
        }

        hbox {
            button("¬") {
                setPrefSize(45.0, 45.0)
                tooltip("Отрицание")
                action { model.expression.value += "¬" }
                style {
                    textFill = Color.BLUE
                    fontSize = 20.px
                }
            }

            button("∧") {
                setPrefSize(45.0, 45.0)
                tooltip("Конъюнкция")
                action { model.expression.value += "∧" }
                style {
                    textFill = Color.BLUE
                    fontSize = 18.px
                }
            }

            button("∨") {
                setPrefSize(45.0, 45.0)
                tooltip("Дизъюнкция")
                action { model.expression.value += "∨" }
                style {
                    textFill = Color.BLUE
                    fontSize = 18.px
                }
            }

            button("→") {
                setPrefSize(45.0, 45.0)
                tooltip("Импликация")
                action { model.expression.value += "→" }
                style {
                    textFill = Color.BLUE
                    fontSize = 18.px
                }
            }

            button("↔") {
                setPrefSize(45.0, 45.0)
                tooltip("Тождество")
                action { model.expression.value += "↔" }
                style {
                    textFill = Color.BLUE
                    fontSize = 18.px
                }
            }

            button("⇦") {
                setPrefSize(45.0, 45.0)
                enableWhen { model.expression.isNotBlank() }
                tooltip("Стереть символ/выражение")
                action { model.expression.value = model.expression.value.substring(0, model.expression.value.lastIndex) }
                longpress { model.expression.value = "" }
                style {
                    textFill = Color.BLUE
                    fontSize = 20.px
                }
            }
        }

        hbox {
            vbox {
                hbox {
                    button("A") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "A" }
                    }

                    button("B") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "B" }
                    }

                    button("C") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "C" }
                    }

                    button("D") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "D" }
                    }

                    button("(") {
                        setPrefSize(45.0, 45.0)
                        tooltip("Открывающая скобка")
                        action { model.expression.value += "(" }
                        style { textFill = Color.BLUE }
                    }
                }

                hbox {
                    button("E") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "E" }
                    }

                    button("F") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "F" }
                    }

                    button("G") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "G" }
                    }

                    button("H") {
                        setPrefSize(45.0, 45.0)
                        action { model.expression.value += "H" }
                    }

                    button(")") {
                        setPrefSize(45.0, 45.0)
                        tooltip("Закрывающая скобка")
                        action { model.expression.value += ")" }
                        style { textFill = Color.BLUE }
                    }
                }
            }

            button("=") {
                setPrefSize(45.0, 45.0)
                useMaxHeight = true
                enableWhen { model.expression.isNotBlank() }
                tooltip("Результат")
                action { controller.setExpression(model.expression.value) }
                style {
                    backgroundColor += Color.LIGHTBLUE
                    fontSize = 20.px
                }
            }
        }
    }

    override fun onDock() {
        model.expression.value = ""
        model.errorMsg.value = ""
        model.rows.value = ""
        model.columns.value = ""
        model.operations.value = ""
        model.args.value = ""
        model.clearDecorators()
    }
}