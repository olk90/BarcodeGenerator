package org.tornadofx.barcodegenerator.controller.exceptions


class DuplicateEntryException(identifier: String) : Exception() {
    override val message = "$identifier already exists!"
}