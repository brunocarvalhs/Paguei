package br.com.brunocarvalhs.payflow.domain.listeners

interface BarcodeScanListener {
    fun onScanSuccess(barcode: String)
    fun onScanError(error: String)
}