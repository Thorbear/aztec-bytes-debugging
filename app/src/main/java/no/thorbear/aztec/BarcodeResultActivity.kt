package no.thorbear.aztec

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.Barcode
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import no.thorbear.aztec.barcodescanner.ThorBarcodeHandler
import no.thorbear.aztec.databinding.ActivityBarcodeResultBinding

class BarcodeResultActivity : AppCompatActivity() {

    private val barcodeHandler = ThorBarcodeHandler(printOutput = ::printOutput)
    private val onStopDisposables = CompositeDisposable()
    private val mainThreadScheduler = AndroidSchedulers.mainThread()

    private lateinit var binding: ActivityBarcodeResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarcodeResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val barcode: Barcode = currentBarcode.let {
            if (it == null) {
                printOutputTitle("No barcode detected!")
                return
            } else {
                printOutputTitle("Aztec barcode detected")
                it
            }
        }

        barcodeHandler.handle(barcode)
        printOutputTitle("Aztec barcode processing completed")
    }

    override fun onStop() {
        onStopDisposables.clear()
        super.onStop()
    }

    private fun printOutputTitle(string: String) {
        printOutput("\n--- $string ---")
    }

    @SuppressLint("SetTextI18n")
    private fun printOutput(string: String) {
        Single.just(string)
            .subscribeOn(mainThreadScheduler)
            .observeOn(mainThreadScheduler)
            .subscribeBy(
                onError = { Log.wtf("THOR", "Error printing log message") },
                onSuccess = {
                    Log.d("THOR", it)
                    binding.output.text = "${binding.output.text}\n$it"
                }
            )
            .addTo(onStopDisposables)
    }

    companion object {
        var currentBarcode: Barcode? = null
    }
}
