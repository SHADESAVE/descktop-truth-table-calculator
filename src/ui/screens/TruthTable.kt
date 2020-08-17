package ui.screens

import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.ViewModel

class TruthTableViewModel : ItemViewModel<TableRow>() {
    val errorMsg = bind { SimpleStringProperty("") }
    val expression = bind { SimpleStringProperty("") }
    val columns = bind { SimpleStringProperty("") }
    val rows = bind { SimpleStringProperty("") }
    val operations = bind { SimpleStringProperty("") }
    val args = bind { SimpleStringProperty("") }
}

class TableRow {
    val columnsProperty = SimpleStringProperty()
    val rowsProperty = SimpleStringProperty()
}