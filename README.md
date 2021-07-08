# BarcodeGenerator

This application can generate simple barcode labels to either DOCX or PNG format. A label may contain the barcode, its
human-readable content, and a name that has to be unique.

![main view](docs/barcode_generator_main_view.png?raw=true "Title")

Before generating the labels, it is recommended to choose a target destination. If this field is left empty, the user's
home directory will be chosen as target. With the target format PNG there will be one file per label generated. The DOCX
as target format will create a single `docx` file that contains the labels in the same order they were created. These
options are set by the checkboxes in the upper part of the dialog. In both cases the naming of the output consist of the
barcode type and the timestamp of creation. After all labels have been added to the table the generation will start by
clicking the print button below the table or by pressing `Ctrl + G`.

The table contents can be imported via CSV file. The files must consist of two columns, one for the name and one for the
numerical barcode. Valid separators are `,` and `;`.

## Install

Download the latest release and unzip it. Navigate to the `bin` directory and execute either the shell
script `BarcodeGenerator` (for Linux and Mac users) or the  `BarcodeGenerator.bat` (Windows users). Alternatively you
can run the jar file on Linux, Mac and Windows via command line: `java -jar BarcodeGenerator<version>.jar`.

## Upcoming Features

- Barcode generation pane with support for multiple formats
- Custom naming patterns for output files
- Validation of barcodes before generating labels
- Feature requests welcome!
