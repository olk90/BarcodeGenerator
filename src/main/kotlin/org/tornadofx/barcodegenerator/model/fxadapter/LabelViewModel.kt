package org.tornadofx.barcodegenerator.model.fxadapter

import org.tornadofx.barcodegenerator.model.data.Label
import tornadofx.*


class LabelViewModel(var label: Label) : ViewModel() {

    val nameProperty = bind { label.nameProperty }
    val barcodeProperty = bind { label.barcodeProperty }
}