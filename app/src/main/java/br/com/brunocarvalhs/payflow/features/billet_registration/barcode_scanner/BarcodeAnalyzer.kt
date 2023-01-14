package br.com.brunocarvalhs.payflow.features.billet_registration.barcode_scanner

import android.annotation.SuppressLint
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import br.com.brunocarvalhs.payflow.domain.listeners.BarcodeScanListener
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage
class BarcodeAnalyzer(
    private val barcodeListener: BarcodeScanListener,
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // Pass image to the scanner and have it do its thing
            scanner.process(image).addOnSuccessListener { barcodes ->
                // Task completed successfully
                for (barcode in barcodes) {
                    barcodeListener.onScanSuccess(barcode.rawValue ?: "")
                }
            }.addOnFailureListener {
                barcodeListener.onScanError(it.message ?: "")
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}