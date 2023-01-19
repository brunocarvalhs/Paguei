package br.com.brunocarvalhs.domain.listeners

interface BarcodeScanListener {
    fun onScanSuccess(barcode: String)
    fun onScanError(error: String)
}