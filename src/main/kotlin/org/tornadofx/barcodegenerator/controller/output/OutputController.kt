package org.tornadofx.barcodegenerator.controller.output

import javafx.scene.control.ButtonType
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.krysalis.barcode4j.BarcodeException
import org.krysalis.barcode4j.HumanReadablePlacement
import org.krysalis.barcode4j.impl.AbstractBarcodeBean
import org.krysalis.barcode4j.impl.code128.Code128Bean
import org.krysalis.barcode4j.impl.code128.EAN128Bean
import org.krysalis.barcode4j.impl.upcean.EAN13Bean
import org.krysalis.barcode4j.impl.upcean.EAN8Bean
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider
import org.tornadofx.barcodegenerator.model.data.Configuration
import org.tornadofx.barcodegenerator.model.data.enums.FileType
import org.tornadofx.barcodegenerator.model.data.Label
import org.tornadofx.barcodegenerator.model.data.enums.BarcodeType
import tornadofx.*
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO
import kotlin.math.roundToInt


class OutputController : Controller() {

    var model = Configuration.model

    private var barcodeType: BarcodeType = model.formatProperty.value
    private var printName: Boolean = model.printNameProperty.value
    private var printBarcode: Boolean = model.printBarcodeProperty.value
    private var outputPath: String = model.outputPathProperty.value

    @Throws(BarcodeException::class, IOException::class)
    fun writeBarcodesToImage(valueList: MutableList<Label>) {

        println("Write images to $outputPath")

        // create subdirectory for output files
        val localDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
        val formatted = localDateTime.format(formatter)
        val fileName = "/${barcodeType}_$formatted"

        if (model.fileTypeProperty.value == FileType.PNG) {

            outputPath += "$fileName/"

            // create directory, if needed
            val mkdirs = File(outputPath).mkdirs()
            if (mkdirs)
                println("output target created")

            for (label in valueList) {
                val bean: AbstractBarcodeBean = createBean()
                val image = File(outputPath + label.name + "_" + barcodeType.name + "." + FileType.PNG.suffix)
                writeImage(image, bean, label)
            }

        } else {
            val docx = File("$outputPath$fileName." + FileType.DOCX.suffix)
            val document = XWPFDocument()

            for (label in valueList) {
                val bean: AbstractBarcodeBean = createBean()
                val stream = FileOutputStream(docx)
                val image = File("${label.name}_${barcodeType.name}." + FileType.PNG.suffix)
                writeImage(image, bean, label)

                // insert the image into the actual file
                // dimensions width and height depend on canvasProvider
                addImageToFile(image, document, stream, label.width, label.height)

                // after inserting the image into the document, it should be deleted
                image.delete()
                stream.flush()
                stream.close()
            }

        }

        confirm(
                header = "Clear Table?",
                confirmButton = ButtonType.YES,
                cancelButton = ButtonType.NO,
                actionFn = {
                    valueList.clear()
                }


        )

    }

    private fun createBean(): AbstractBarcodeBean {
        return when (barcodeType) {
            BarcodeType.EAN_8 -> EAN8Bean()
            BarcodeType.EAN_13 -> EAN13Bean()
            BarcodeType.CODE_128 -> Code128Bean()
            else -> EAN128Bean()
        }
    }

    private fun writeImage(image: File, bean: AbstractBarcodeBean, label: Label) {
        val outputStream = FileOutputStream(image)

        try {
            val bufferedImage = renderBarcode(label, bean)
            // convert graphic to png
            ImageIO.write(bufferedImage, "png", image)

            // set up dimensions for image in document file
            if (model.fileTypeProperty.value == FileType.DOCX) {
                label.width = bufferedImage.width
                label.height = bufferedImage.height
                println("Set dimensions of ${label.name}: w=${label.width}, h=${label.height}")
            }

            outputStream.close()

        } catch (e: IllegalArgumentException) {
            println("Ignore $label from value list to avoid strange system behaviour.")
        }

    }

    private fun renderBarcode(label: Label, bean: AbstractBarcodeBean): BufferedImage {
        val scaleFactor = 2.835 // s. https://sourceforge.net/p/barcode4j/mailman/message/2797890/
        bean.moduleWidth = 1.0
        bean.barHeight = 40.0

        bean.fontSize = 15.0
        bean.quietZone = 10.0
        bean.verticalQuietZone = bean.fontSize + 5
        bean.doQuietZone(true)

        println(label)
        val dim = bean.calcDimensions(label.barcode)

        val width = (dim.getWidth(0) + 20) * scaleFactor
        val height = (dim.getHeight(0) + 20) * scaleFactor

        val bufferedImage = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()
        g2d.scale(scaleFactor, scaleFactor)

        g2d.color = Color.WHITE
        g2d.fillRect(0, 0, width.toInt(), height.toInt())
        g2d.color = Color.BLACK

        try {

            // orientation must be 0, 90, 180, 270, -90, -180 or -270
            val java2DCanvasProvider = Java2DCanvasProvider(g2d, 0)

            // currently no idea, why this is necessary
            val scaledWidth = (width / scaleFactor).roundToInt()

            if (printName)
                drawCenteredString(g2d, label.name, scaledWidth, 15)

            bean.msgPosition = HumanReadablePlacement.HRP_NONE
            bean.generateBarcode(java2DCanvasProvider, label.barcode)

            if (printBarcode) {
                val barcodeYCoord = 20 + bean.barHeight.toInt() + bean.fontSize.toInt()
                drawCenteredString(g2d, label.barcode, scaledWidth, barcodeYCoord)
            }

        } catch (e: IllegalArgumentException) {
            g2d.drawRect(0, 0, width.toInt() - 1, height.toInt() - 1)
            g2d.drawString(label.barcode, 2, height.toInt() - 3)
        }

        g2d.dispose()

        return bufferedImage
    }

    // source: https://stackoverflow.com/a/27740330
    private fun drawCenteredString(graphics2d: Graphics2D, text: String, width: Int, height: Int) {
        val metrics = graphics2d.getFontMetrics(graphics2d.font)
        val x = (width - metrics.stringWidth(text)) / 2
        val y = height
        println("Draw text \"$text\" at coordinates ($x,$y)")
        graphics2d.drawString(text, x, y)
    }

}
