package org.tornadofx.barcodegenerator.view

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ContextMenu
import javafx.scene.control.ToggleGroup
import javafx.stage.FileChooser
import org.tornadofx.barcodegenerator.model.data.BarcodeType
import org.tornadofx.barcodegenerator.model.data.Configuration
import org.tornadofx.barcodegenerator.model.data.FileType
import tornadofx.*
import java.io.File

class MainView : View("Barcode Generator") {

    private val fileToggleGroup = ToggleGroup()
    private val formatToggleGroup = ToggleGroup()

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
                        fieldset {

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
                                radiobutton(FileType.PNG.suffix, fileToggleGroup) {
                                    fileToggleGroup.selectToggle(this)
                                    whenSelected {
                                        println("Set fileType PNG")
                                        model.fileTypeProperty.setValue(FileType.PNG)
                                    }
                                }

                                radiobutton(FileType.DOCX.suffix, fileToggleGroup) {
                                    whenSelected {
                                        println("Set fileType DOCX")
                                        model.fileTypeProperty.setValue(FileType.DOCX)
                                    }
                                }
                            }

                            field("Format") {
                                radiobutton("Code-128", formatToggleGroup) {
                                    formatToggleGroup.selectToggle(this)
                                    whenSelected {
                                        println("Set barcode format Code-128")
                                        model.formatProperty.setValue(BarcodeType.CODE_128)
                                    }
                                }
                                radiobutton("EAN-8", formatToggleGroup) {
                                    whenSelected {
                                        println("Set barcode format EAN-8")
                                        model.formatProperty.setValue(BarcodeType.EAN_8)
                                    }
                                }
                                radiobutton("EAN-13", formatToggleGroup) {
                                    whenSelected {
                                        println("Set barcode format EAN-13")
                                        model.formatProperty.setValue(BarcodeType.EAN_13)
                                    }
                                }
                                radiobutton("EAN-128", formatToggleGroup) {
                                    whenSelected {
                                        println("Set barcode format EAN-128")
                                        model.formatProperty.setValue(BarcodeType.EAN_128)
                                    }
                                }
                            }
                        }

                        fieldset("Input") {
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