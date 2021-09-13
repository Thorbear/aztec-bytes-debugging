package no.thorbear.aztec.barcodescanner

import com.google.mlkit.vision.barcode.Barcode

class ThorBarcodeHandler(
    private val printOutput: (String) -> Unit = { println(it) },
) {

    fun handle(barcode: Barcode) {
        barcode.rawBytes.let {
            if (it == null) {
                printOutput("No raw bytes!")
            } else {
                printOutput("Raw bytes:\n${it.toHexString()}")
            }
        }
    }

    private fun ByteArray.toHexString() = joinToString("") { "%02X".format(it) }

}
