package ui.fragments

import tornadofx.Fragment
import tornadofx.label
import tornadofx.paddingAll
import tornadofx.vbox
import ui.screens.TruthTableViewModel


class PopupFragment : Fragment() {
    private val model: TruthTableViewModel by inject()

    override val root = vbox {
        paddingAll = 5
        label(model.errorMsg) {
            isWrapText = true
        }
    }
}