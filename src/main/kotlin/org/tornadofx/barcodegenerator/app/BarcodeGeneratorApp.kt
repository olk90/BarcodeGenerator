package org.tornadofx.barcodegenerator.app

import javafx.stage.Stage
import org.tornadofx.barcodegenerator.view.MainView
import tornadofx.*

class BarcodeGeneratorApp : App(MainView::class) {

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 450.0
        stage.height = 800.0

        stage.minWidth = 450.0
        stage.minHeight = 800.0
    }
}