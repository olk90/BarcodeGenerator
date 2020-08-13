package org.tornadofx.barcodegenerator.view

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ContextMenu
import javafx.stage.FileChooser
import org.tornadofx.barcodegenerator.model.data.Configuration
import org.tornadofx.barcodegenerator.model.data.enums.BarcodeType
import org.tornadofx.barcodegenerator.model.data.enums.FileType
import tornadofx.*
import java.io.File

class MainView : View("Barcode Generator") {

    private val userHome = File(System.getProperty("user.home"))

    private val tableView: BarcodeTableView by inject()

    private val outputProperty = SimpleObjectProperty<String>()
    private var outputPath by outputProperty

    private val inputProperty = SimpleObjectProperty<String>()
    private var inputPath by inputProperty

    private val model = Configuration.model

    override val root =
            squeezebox {
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

                                button("Select") {
                                    action {
                                        val directory = chooseFile("Load data from ...",
                                                filters = arrayOf(FileChooser.ExtensionFilter("CSV file", "*.csv"))) {
                                            initialDirectory = userHome
                                        }

                                        inputPath = directory.toString()
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
                                        // TODO set without Enter button
                                        println("Set new output path: $text")
                                        model.outputPathProperty.setValue(text)
                                    }
                                }

                                button("Select") {
                                    action {
                                        val directory = chooseDirectory("Write output to ...") {
                                            initialDirectory = userHome
                                        }

                                        outputPath = directory.toString()
                                        println("Set new output path: $outputPath")
                                        model.outputPathProperty.setValue(outputPath)
                                    }
                                }
                            }
                        }

                    }

                    borderpane {
                        center = tableView.root
                    }

                }

//                TODO implement later ...
//                titledpane("Barcodes") {
//
//                    label("Not implemented") {
//                        addClass(Styles.heading)
//                    }
//
//                }
            }

}