package no.thorbear.aztec.barcodescanner

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import no.thorbear.aztec.BarcodeResultActivity
import no.thorbear.aztec.VisionProcessorBase
import no.thorbear.aztec.java.GraphicOverlay

class BarcodeScannerProcessor(private val context: Context) : VisionProcessorBase<List<Barcode>>(context) {

    // Note that if you know which format of barcode your app is dealing with, detection will be
    // faster to specify the supported barcode formats one by one, e.g.
    // BarcodeScannerOptions.Builder()
    //     .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
    //     .build();
    private val barcodeScannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_AZTEC)
        .build()
    private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions)

    override fun stop() {
        super.stop()
        barcodeScanner.close()
    }

    override fun detectInImage(image: InputImage): Task<List<Barcode>> {
        return barcodeScanner.process(image)
    }

    override fun onSuccess(results: List<Barcode>, graphicOverlay: GraphicOverlay) {
        if (results.isEmpty()) {
            // No barcode has been detected
            return
        }
        BarcodeResultActivity.currentBarcode = results.firstOrNull()
        stop()
        context.startActivity(Intent(context, BarcodeResultActivity::class.java))
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Barcode detection failed", e)
    }

    companion object {
        private const val TAG = "BarcodeScannerProcessor"
    }
}
