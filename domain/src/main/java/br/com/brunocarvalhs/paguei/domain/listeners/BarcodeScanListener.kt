package br.com.brunocarvalhs.paguei.domain.listeners

interface BarcodeScanListener {
    fun onScanSuccess(barcode: String)
    fun onScanError(error: String)
}