package de.olk90.barcodegenerator.model.data

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import de.olk90.barcodegenerator.model.data.enums.BarcodeType
import de.olk90.barcodegenerator.model.data.enums.FileType
import de.olk90.barcodegenerator.model.fxadapter.ConfigurationViewModel
import java.io.File

/**
 * Container class for all information to passed to OutputController.
 */
object Configuration {

    var model: ConfigurationViewModel

    var outputPath: File = File(System.getProperty("user.home"))
    val outputPathProperty = SimpleStringProperty(this, "outputPath", outputPath.toString())

    var printBarcode: Boolean = false
    val printBarcodeProperty = SimpleBooleanProperty(this, "printBarcode", printBarcode)

    var printName: Boolean = false
    val printNameProperty = SimpleBooleanProperty(this, "printName", printName)

    var fileType: FileType = FileType.PNG
    val fileTypeProperty = SimpleObjectProperty<FileType>(this, "fileType", fileType)

    var format: BarcodeType = BarcodeType.CODE_128
    val formatProperty = SimpleObjectProperty<BarcodeType>(this, "format", format)

    var inputPath: File = File(System.getProperty("user.home"))
    val inputPathProperty = SimpleStringProperty(this, "inputPath", inputPath.toString())

    init {
        println("Init Configuration")
        model = ConfigurationViewModel()
    }
}