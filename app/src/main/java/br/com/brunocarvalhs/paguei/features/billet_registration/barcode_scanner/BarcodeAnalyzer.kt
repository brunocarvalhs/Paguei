package br.com.brunocarvalhs.paguei.features.billet_registration.barcode_scanner

import android.content.Context
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import br.com.brunocarvalhs.domain.listeners.BarcodeScanListener
import br.com.brunocarvalhs.paguei.R
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage
class BarcodeAnalyzer(
    private val context: Context,
    private val barcodeListener: BarcodeScanListener,
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
        Barcode.FORMAT_ITF, Barcode.FORMAT_QR_CODE
    ).build()

    private val scanner = BarcodeScanning.getClient(options)

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image).addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    when (barcode.valueType) {
                        Barcode.FORMAT_ITF -> {
                            if (isBarcodeValidated(barcode.rawValue)) {
                                val result = calculaLinha(barcode.rawValue)
                                barcodeListener.onScanSuccess(result)
                            } else {
                                barcodeListener.onScanError(context.getString(R.string.barcode_analyzer_on_scan_error_format_itf))
                            }
                        }
                        Barcode.TYPE_TEXT -> {
                            barcodeListener.onScanSuccess(extractValue(barcode.rawValue))
                        }
                        else -> barcodeListener.onScanError(context.getString(R.string.barcode_analyzer_on_scan_error_not_support))
                    }
                }
            }.addOnFailureListener {
                barcodeListener.onScanError(it.message.orEmpty())
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }

    private fun isBarcodeValidated(value: String?): Boolean {
        return false
    }

    private fun extractValue(value: String?): String? {
        val pattern1 = "^[0-9]{10}\$".toRegex()
        val pattern2 = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
        val pattern3 = "^[+]?[0-9]{11,}\$".toRegex()
        val pattern4 =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$".toRegex()
        val pattern5 = "^[0-9]{14}\$".toRegex()

        return when {
            pattern1.matches(value.orEmpty()) -> value
            pattern2.matches(value.orEmpty()) -> value
            pattern3.matches(value.orEmpty()) -> value
            pattern4.matches(value.orEmpty()) -> value
            pattern5.matches(value.orEmpty()) -> value
            else -> null
        }
    }

    fun calculaLinha(barra: String?): String? {
        val linha = barra?.replace("[^0-9]".toRegex(), "")
        if (linha?.length != 44) {
            return null
        }
        val campo1 = linha.substring(0, 4) + linha.substring(19, 20) + '.' + linha.substring(20, 24)
        val dv1 = modulo10(campo1)
        val campo2 = linha.substring(24, 29) + '.' + linha.substring(29, 34)
        val dv2 = modulo10(campo2)
        val campo3 = linha.substring(34, 39) + '.' + linha.substring(39, 44)
        val dv3 = modulo10(campo3)
        val campo4 = linha.substring(4, 5)
        val campo5 = linha.substring(5, 19)
        val validation = modulo11Banco(linha.substring(0, 4) + linha.substring(5, 44))
        return if (validation != Integer.valueOf(campo4)) {
            null
        } else {
            "${campo1 + dv1} ${campo2 + dv2} ${campo3 + dv3} $campo4 $campo5"
        }
    }

    private fun modulo10(numero: String): Int {
        var numero = numero
        numero = numero.replace("\\D".toRegex(), "")
        var soma = 0
        var peso = 2
        var contador = numero.length - 1
        while (contador >= 0) {
            var multiplicacao = Integer.valueOf(numero.substring(contador, contador + 1)) * peso
            if (multiplicacao >= 10) {
                multiplicacao = 1 + (multiplicacao - 10)
            }
            soma += multiplicacao
            peso = if (peso == 2) {
                1
            } else {
                2
            }
            contador -= 1
        }
        var digito = 10 - soma % 10
        if (digito == 10) digito = 0
        return digito
    }

    private fun modulo11Banco(numero: String): Int {
        var numero = numero
        numero = numero.replace("\\D".toRegex(), "")
        var soma = 0
        var peso = 2
        val base = 9
        val contador = numero.length - 1
        for (i in contador downTo 0) {
            soma += Integer.valueOf(numero.substring(i, i + 1)) * peso
            if (peso < base) {
                peso++
            } else {
                peso = 2
            }
        }
        var digito = 11 - soma % 11
        if (digito > 9) digito = 0
        if (digito == 0) digito = 1
        return digito
    }
}