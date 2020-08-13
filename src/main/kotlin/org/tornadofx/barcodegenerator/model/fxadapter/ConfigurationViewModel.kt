package org.tornadofx.barcodegenerator.model.fxadapter

import org.tornadofx.barcodegenerator.model.data.Configuration
import tornadofx.*


class ConfigurationViewModel : ViewModel() {

    val outputPathProperty = bind { Configuration.outputPathProperty }
    val printBarcodeProperty = bind { Configuration.printBarcodeProperty }
    val printNameProperty = bind { Configuration.printNameProperty }
    val fileTypeProperty = bind { Configuration.fileTypeProperty }
    val formatProperty = bind { Configuration.formatProperty }
    val inputPathProperty = bind { Configuration.inputPathProperty }

}