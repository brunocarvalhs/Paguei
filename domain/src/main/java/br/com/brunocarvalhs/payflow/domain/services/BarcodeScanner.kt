package br.com.brunocarvalhs.payflow.domain.services

import br.com.brunocarvalhs.payflow.domain.listeners.BarcodeScanListener

interface BarcodeScanner {
    fun startScan()
    fun stopScan()
    fun setBarcodeScanListener(listener: BarcodeScanListener)
}