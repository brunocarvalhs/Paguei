package br.com.brunocarvalhs.paguei.features.billet_registration.barcode_scanner

import android.annotation.SuppressLint
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import br.com.brunocarvalhs.paguei.domain.listeners.BarcodeScanListener
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
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image).addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    barcodeListener.onScanSuccess(barcode.rawValue ?: "")
                    break
                }
            }.addOnFailureListener {
                barcodeListener.onScanError(it.message ?: "")
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}