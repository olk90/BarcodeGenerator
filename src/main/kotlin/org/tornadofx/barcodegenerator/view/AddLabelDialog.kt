package org.tornadofx.barcodegenerator.view

import org.tornadofx.barcodegenerator.controller.LabelController
import org.tornadofx.barcodegenerator.model.data.Label
import org.tornadofx.barcodegenerator.model.fxadapter.LabelViewModel
import tornadofx.*


class AddLabelDialog : Fragment() {

    private val controller: LabelController by inject()
    private val model = LabelViewModel(Label("", ""))

    override val root = form {
        fieldset("New Label") {

            field("Name") {
                textfield(model.nameProperty) {
                    // Make sure we get focus
                    whenDocked { requestFocus() }
                }
            }

            field("Barcode") {
                textfield(model.barcodeProperty)
                button("Random Barcode") {
                    action {
                        // TODO implement random barcode generation depending on preset format
                    }
                }
            }
        }

        button("Add") {
            shortcut(enter)
            action {
                controller.createLabel(model)
                close()
            }

            disableWhen(model.barcodeProperty.isBlank())
        }
    }

}