package br.com.brunocarvalhs.billet_registration.barcode_scanner

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import br.com.brunocarvalhs.domain.listeners.BarcodeScanListener
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage
class BarcodeAnalyzer(
    private val barcodeListener: BarcodeScanListener,
) : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanBarcodes(image)
        }
    }

    private fun scanBarcodes(image: InputImage) {
        var value: String? = null
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_ITF
            )
            .build()

        val scanner = BarcodeScanning.getClient(options)

        val result = scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    value = barcode.rawValue
                }
            }
            .addOnFailureListener {

            }

        result.addOnSuccessListener { value?.let { barcodeListener.onScanSuccess(it) } }
            .addOnFailureListener { barcodeListener.onScanError(it.message) }
    }
}