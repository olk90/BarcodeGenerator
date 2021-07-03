package de.olk90.barcodegenerator.model.fxadapter

import de.olk90.barcodegenerator.model.data.Label
import tornadofx.*


class LabelViewModel(var label: Label) : ViewModel() {

    val nameProperty = bind { label.nameProperty }
    val barcodeProperty = bind { label.barcodeProperty }
}