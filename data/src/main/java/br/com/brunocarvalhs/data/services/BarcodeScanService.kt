package br.com.brunocarvalhs.data.services

import br.com.brunocarvalhs.payflow.domain.listeners.BarcodeScanListener
import br.com.brunocarvalhs.payflow.domain.services.BarcodeScanner

class BarcodeScanService : BarcodeScanner {
    private var listener: BarcodeScanListener? = null

    override fun startScan() {
        // implementação de iniciar a leitura do código de barra
    }

    override fun stopScan() {
        // implementação de parar a leitura do código de barra
    }

    override fun setBarcodeScanListener(listener: BarcodeScanListener) {
        this.listener = listener
    }

    // função para notificar o listener de sucesso
    private fun notifyScanSuccess(barcode: String) {
        listener?.onScanSuccess(barcode)
    }

    // função para notificar o listener de erro
    private fun notifyScanError(error: String) {
        listener?.onScanError(error)
    }
}