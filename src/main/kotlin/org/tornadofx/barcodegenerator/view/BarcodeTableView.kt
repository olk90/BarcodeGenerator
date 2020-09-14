package org.tornadofx.barcodegenerator.view

import javafx.geometry.Pos
import javafx.scene.control.ContextMenu
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import org.tornadofx.barcodegenerator.controller.LabelController
import org.tornadofx.barcodegenerator.model.data.Label
import org.tornadofx.barcodegenerator.view.extensions.align
import tornadofx.*

class BarcodeTableView : View() {

    private val labelController: LabelController by inject()

    override val root = tableview(labelController.labels) {

        makeIndexColumn().apply {
            align(Pos.CENTER)
        }

        column("Name", Label::nameProperty).apply {
            align(Pos.CENTER)
            pctWidth(40)
        }
        column("Barcode", Label::barcodeProperty).apply {
            align(Pos.CENTER)
            pctWidth(40)
        }

        labelController.emptyProperty.set(true)

        // column width should depend on both, the cell content and the window's dimensions
        columnResizePolicy =  SmartResize.POLICY
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
        }

    }

    private fun newLabel() {
        openInternalWindow(AddLabelDialog::class)
        labelController.emptyProperty.set(false)
    }

    private fun TableView<Label>.deleteSelected() {
        selectedItem?.let { labelController.labels.remove(it) }
        // disable controls again, if the last item was deleted
        if (labelController.labels.isEmpty())
            labelController.emptyProperty.set(true)

    }

}
