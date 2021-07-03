package de.olk90.barcodegenerator.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ContextMenu
import javafx.stage.FileChooser
import de.olk90.barcodegenerator.controller.InputController
import de.olk90.barcodegenerator.controller.LabelController
import de.olk90.barcodegenerator.controller.output.OutputController
import de.olk90.barcodegenerator.model.data.Configuration
import de.olk90.barcodegenerator.model.data.enums.BarcodeType
import de.olk90.barcodegenerator.model.data.enums.FileType
import de.olk90.barcodegenerator.view.extensions.icon
import tornadofx.*
import java.io.File

class MainView : View("Barcode Generator") {

    private val userHome = File(System.getProperty("user.home"))

    private val tableView: BarcodeTableView by inject()
    private val labelController: LabelController by inject()
    private val inputController: InputController by inject()

    private val outputProperty = SimpleObjectProperty<String>()
    private var outputPath by outputProperty

    private val inputProperty = SimpleObjectProperty<String>()
    private var inputPath by inputProperty

    private val model = Configuration.model

    override val root = squeezebox {
        titledpane("Labels") {
            form {
                fieldset("Settings") {
                    field("Layout") {
                        checkbox("Print Barcode") {
                            bind(model.printBarcodeProperty)
                        }
                        checkbox("Print Name") {
                            bind(model.printNameProperty)
                        }
                    }

                    field("Output Type") {
                        choicebox(model.fileTypeProperty, values = FileType.values().asList()) {
                            prefWidth = 150.0
                            selectionModel.selectFirst()
                        }
                    }

                    field("Format") {
                        choicebox(model.formatProperty, values = BarcodeType.values().asList()) {
                            prefWidth = 150.0
                            selectionModel.selectFirst()
                        }
                    }
                }

                fieldset("I/O") {
                    field("Input File:") {
                        textfield {
                            bind(inputProperty)

                            contextMenu = ContextMenu().apply {
                                item("Clear") {
                                    action {
                                        inputPath = ""
                                    }
                                }
                            }
                        }

                        button{
                            tooltip("Select directory")
                            addClass("icon-only")
                            graphic = icon(FontAwesomeIcon.FOLDER_OPEN)
                            action {
                                val directory = chooseFile("Load data from ...",
                                        filters = arrayOf(FileChooser.ExtensionFilter("CSV file", "*.csv"))) {
                                    initialDirectory = userHome
                                }
                                val csv = directory.first()
                                inputPath = csv.absolutePath
                                inputController.readCsvFile(csv)
                            }
                        }
                    }

                    field("Output Path:") {
                        textfield {
                            bind(outputProperty)
                            contextMenu = ContextMenu().apply {
                                item("Clear") {
                                    action {
                                        outputPath = ""
                                    }
                                }
                            }
                            action {
                                println("Set new output path: $text")
                                model.outputPathProperty.setValue(text)
                            }
                        }

                        button {
                            tooltip("Select directory")
                            addClass("icon-only")
                            graphic = icon(FontAwesomeIcon.FOLDER_OPEN)
                            action {
                                val directory = chooseDirectory("Write output to ...") {
                                    initialDirectory = userHome
                                }

                                if (directory != null) {
                                    outputPath = directory.absolutePath
                                    println("Set new output path: $outputPath")
                                    model.outputPathProperty.setValue(outputPath)
                                }
                            }
                        }
                    }
                    add(tableView.root)
                }
            }

            buttonbar {
                button {
                    tooltip("Generate labels [$generate]")
                    addClass("icon-only")
                    graphic = icon(FontAwesomeIcon.PRINT)
                    action {
                        val controller = OutputController()
                        controller.writeBarcodesToImage(labelController.labels)
                    }
                    shortcut(generate)
                    disableWhen(labelController.emptyProperty)
                }
                button {
                    tooltip("Clear input [$clearInput]")
                    addClass("icon-only")
                    graphic = icon(FontAwesomeIcon.ERASER)
                    action {
                        labelController.labels.clear()
                        labelController.emptyProperty.set(true)
                    }
                    shortcut(clearInput)
                    disableWhen(labelController.emptyProperty)
                }
            }
        }

        titledpane("Barcodes") {
            isExpanded = false
            label("Not yet implemented")
        }
    }
}