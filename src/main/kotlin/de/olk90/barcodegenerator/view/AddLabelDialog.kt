package de.olk90.barcodegenerator.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.olk90.barcodegenerator.controller.LabelController
import de.olk90.barcodegenerator.model.data.Label
import de.olk90.barcodegenerator.model.fxadapter.LabelViewModel
import de.olk90.barcodegenerator.view.extensions.icon
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
                button {
                    tooltip("Generate random barcode")
                    addClass("icon-only")
                    graphic = icon(FontAwesomeIcon.RANDOM)
                    action {
                        information("Not yet implemented")
                    }
                }
            }
        }

        buttonbar {
            button {
                tooltip("Add label [$enter]")
                addClass("icon-only")
                graphic = icon(FontAwesomeIcon.PLUS)
                shortcut(enter)
                action {
                    controller.createLabel(model)
                    close()
                }

                disableWhen(model.barcodeProperty.isBlank())
            }
        }
    }

}