package org.tornadofx.barcodegenerator.controller

import org.tornadofx.barcodegenerator.model.data.Label
import tornadofx.*
import java.io.File


class InputController : Controller() {

    private val labelController: LabelController by inject()

    fun readCsvFile(csv: File) {
        csv.inputStream().bufferedReader().useLines { lines ->
            lines.forEachIndexed { index, l ->
                if (index > 0) {
                    val split = l.split(",", ";")
                    val elements = split.size
                    if (elements != 2) {
                        throw InvalidFormatException(l, elements)
                    }
                    val name = split[0]
                    val barcode = split[1]
                    val label = Label(name, barcode)
                    labelController.labels.add(label)
                    labelController.emptyProperty.set(false)
                }

            }
        }
    }
}