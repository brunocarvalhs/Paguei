package br.com.brunocarvalhs.payflow.features.billet_registration.barcode_scanner

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.payflow.domain.listeners.BarcodeScanListener
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

    fun startCamera(
        context: Context,
        previewView: PreviewView,
        listener: BarcodeScanListener
    ) {
        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                imageAnalysis.setAnalyzer(cameraExecutor, BarcodeAnalyzer(listener))
                initCamera()
            }, ContextCompat.getMainExecutor(context))
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.message ?: "")
        }
    }

    fun initCamera() {
        cameraProvider?.unbind(preview)
        cameraProvider?.unbind(imageAnalysis)
    }

    fun stopCamera(lifecycleOwner: LifecycleOwner) {
        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
    }
}