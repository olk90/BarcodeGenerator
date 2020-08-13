package org.tornadofx.barcodegenerator.controller.output

import com.sun.media.sound.InvalidFormatException
import org.apache.poi.util.Units
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


@Throws(IOException::class, InvalidFormatException::class)
fun addImageToFile(image: File, doc: XWPFDocument, fileOutputStream: FileOutputStream, width: Int, height: Int) {

    val title = doc.createParagraph()
    val run = title.createRun()
    title.alignment = ParagraphAlignment.CENTER

    val fileInputStream = FileInputStream(image)
    run.addPicture(fileInputStream, XWPFDocument.PICTURE_TYPE_PNG, image.name, Units.pixelToEMU(width), Units.pixelToEMU(height))
    run.addBreak()
    fileInputStream.close()

    doc.write(fileOutputStream)
}

