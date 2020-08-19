package ui.screens

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.JsonModel
import tornadofx.ViewModel
import tornadofx.observableListOf

val basis = listOf('↔', '→', '∨', '∧', '¬')

class TruthTableViewModel : ViewModel() {
    val errorMsg = bind { SimpleStringProperty("") }
    val expression = bind { SimpleStringProperty("") }
    val columnsCount = bind { SimpleStringProperty("") }
    val rowsCount = bind { SimpleStringProperty("") }
    val operationsCount = bind { SimpleStringProperty("") }
    val argsCount = bind { SimpleStringProperty("") }
    val tableColumns = bind { SimpleListProperty(observableListOf<String>()) }
    val tableRows = bind { SimpleListProperty(observableListOf<List<String>>()) }
    val formulasResult = bind { SimpleStringProperty("") }
}