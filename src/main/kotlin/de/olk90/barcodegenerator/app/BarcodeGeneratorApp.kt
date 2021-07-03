package de.olk90.barcodegenerator.app

import javafx.scene.image.Image
import javafx.stage.Stage
import de.olk90.barcodegenerator.view.MainView
import tornadofx.*

class BarcodeGeneratorApp : App(MainView::class) {

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 450.0
        stage.height = 800.0

        stage.minWidth = 450.0
        stage.minHeight = 800.0

        stage.icons.add(Image("icon.png"))
    }
}