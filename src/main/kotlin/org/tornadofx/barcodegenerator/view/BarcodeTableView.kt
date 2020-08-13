package org.tornadofx.barcodegenerator.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.ContextMenu
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import org.tornadofx.barcodegenerator.controller.LabelController
import org.tornadofx.barcodegenerator.controller.output.OutputController
import org.tornadofx.barcodegenerator.model.data.Label
import tornadofx.*

class BarcodeTableView : View() {

    private val labelController: LabelController by inject()
    private val tableEmptyProperty = SimpleBooleanProperty()

    override val root = tableview(labelController.labels) {
        column("Name", Label::name)
        column("Barcode", Label::barcode)

        tableEmptyProperty.set(true)

        // column width should depend on both, the cell content and the window's dimensions
        columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
        selectionModel.selectionMode = SelectionMode.SINGLE

        contextMenu = ContextMenu().apply {
            item("New Label", keyCombination = newLabel) {
                action {
                    newLabel()
                }
            }

            separator()
            item("Delete Selected", keyCombination = deleteSelected) {
                action {
                    deleteSelected()
                }

                disableWhen(selectionModel.selectedItemProperty().isNull)
            }
            item("Clear Input", keyCombination = clearInput) {
                action {
                    clearInput()
                }
                disableWhen(tableEmptyProperty)
            }

            separator()
            item("Generate", keyCombination = generate) {
                action {
                    generate()
                }
                disableWhen(tableEmptyProperty)
            }

        }

    }

    private fun newLabel() {
        openInternalWindow(AddLabelDialog::class)
        tableEmptyProperty.set(false)
    }

    private fun TableView<Label>.deleteSelected() {
        selectedItem?.let { labelController.labels.remove(it) }
        // disable controls again, if the last item was deleted
        if (labelController.labels.isEmpty())
            tableEmptyProperty.set(true)

    }

    private fun clearInput() {
        labelController.labels.clear()
        tableEmptyProperty.set(true)
    }

    private fun generate() {
        val controller = OutputController()
        controller.writeBarcodesToImage(labelController.labels)
        // TODO generate output depending on settings
    }

}
