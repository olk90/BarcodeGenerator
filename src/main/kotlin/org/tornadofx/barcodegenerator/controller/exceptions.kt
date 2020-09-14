package org.tornadofx.barcodegenerator.controller


class DuplicateEntryException(identifier: String) : Exception() {
    override val message = "$identifier already exists!"
}

class InvalidFormatException(line : String, elements: Int) : Exception() {
    override val message = "Line[$line] has $elements elements. Expected are two: Name and Barcode"
}
