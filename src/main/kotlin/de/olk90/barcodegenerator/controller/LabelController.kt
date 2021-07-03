package de.olk90.barcodegenerator.controller

import javafx.beans.property.SimpleBooleanProperty
import de.olk90.barcodegenerator.model.data.Label
import de.olk90.barcodegenerator.model.fxadapter.LabelViewModel
import tornadofx.*


class LabelController : Controller() {

    var labels = mutableListOf<Label>().asObservable()

    // TODO: how to bind this property directly to the list without setting it manually?
    val emptyProperty = SimpleBooleanProperty()

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