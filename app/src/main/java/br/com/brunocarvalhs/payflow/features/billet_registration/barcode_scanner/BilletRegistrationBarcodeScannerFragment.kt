package br.com.brunocarvalhs.payflow.features.billet_registration.barcode_scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentBilletRegistrationBarcodeScannerBinding
import br.com.brunocarvalhs.payflow.domain.listeners.BarcodeScanListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalGetImage
@AndroidEntryPoint
class BilletRegistrationBarcodeScannerFragment :
    BaseFragment<FragmentBilletRegistrationBarcodeScannerBinding>(), BarcodeScanListener {

    private val viewModel: BilletRegistrationBarcodeScannerViewModel by viewModels()

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) startCamera()
            else {
                Toast.makeText(requireContext(), "Permissão negada", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBilletRegistrationBarcodeScannerBinding.inflate(inflater, container, false)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is BilletRegistrationBarcodeScannerViewState.Error -> this.showError(it.message)
                is BilletRegistrationBarcodeScannerViewState.Success -> this.displayData(it.code)
            }
        }
    }

    private fun displayData(code: String) {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        requestPermission()
    }

    override fun loading() {

    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
                .also { it.setSurfaceProvider(binding.previewView.surfaceProvider) }

            val imageAnalysis = ImageAnalysis.Builder().build()
                .also { it.setAnalyzer(cameraExecutor, BarcodeAnalyzer(this)) }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, "Binding failed! :(", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onScanSuccess(barcode: String) {
        Toast.makeText(requireContext(), barcode, Toast.LENGTH_LONG).show()
    }

    override fun onScanError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}