package org.tornadofx.barcodegenerator.controller

import org.tornadofx.barcodegenerator.controller.exceptions.DuplicateEntryException
import org.tornadofx.barcodegenerator.model.data.Label
import org.tornadofx.barcodegenerator.model.fxadapter.LabelViewModel
import tornadofx.*


class LabelController : Controller() {

    var labels = mutableListOf<Label>().observable()

    fun createLabel(model: LabelViewModel): Label {
        val name = model.nameProperty.value
        val barcode = model.barcodeProperty.value
        val label = Label(name, barcode)

        if (labels.contains(label))
            throw DuplicateEntryException(label.barcode)
        else
            labels.add(label)

        return label
    }
}