package br.com.brunocarvalhs.paguei.features.billet_registration.barcode_scanner

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.billet_registration.barcode_scanner.BilletRegistrationBarcodeScannerViewState
import br.com.brunocarvalhs.commons.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalGetImage
@HiltViewModel
class BilletRegistrationBarcodeScannerViewModel @Inject constructor() :
    BaseViewModel<BilletRegistrationBarcodeScannerViewState>() {

    var cameraProvider: ProcessCameraProvider? = null

    fun barcodeScanner(result: String?) {
        viewModelScope.launch {
            mutableState.value = BilletRegistrationBarcodeScannerViewState.Loading
            try {
                result?.let {
                    mutableState.value = BilletRegistrationBarcodeScannerViewState.Success(result)
                }
            } catch (error: Exception) {
                mutableState.value = BilletRegistrationBarcodeScannerViewState.Error(error.message)
            }
        }
    }
}