package br.com.brunocarvalhs.paguei.features.billet_registration.barcode_scanner

import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@ExperimentalGetImage
@HiltViewModel
class BilletRegistrationBarcodeScannerViewModel @Inject constructor() :
    BaseViewModel<BilletRegistrationBarcodeScannerViewState>() {

    var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    val imageAnalysis = ImageAnalysis.Builder().build()

    val preview = Preview.Builder().build()

    var cameraProvider: ProcessCameraProvider? = null

    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

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