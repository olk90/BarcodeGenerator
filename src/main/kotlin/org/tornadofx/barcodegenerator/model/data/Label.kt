package org.tornadofx.barcodegenerator.model.data

import javafx.beans.property.SimpleStringProperty


data class Label(val name: String, val barcode: String) {

    val nameProperty = SimpleStringProperty(this, "name", name)
    val barcodeProperty = SimpleStringProperty(this, "barcode", barcode)

    // for internal use only!
    var width: Int = 0
    var height: Int = 0

    override fun equals(other: Any?): Boolean {
        return if (other !is Label)
            false
        else
            barcode == other.barcode
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + barcode.hashCode()
        return result
    }

    override fun toString(): String {
        return "(${this.name}, ${this.barcode})"
    }
}