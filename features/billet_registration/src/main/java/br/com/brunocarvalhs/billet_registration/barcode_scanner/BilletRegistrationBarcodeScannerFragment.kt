package br.com.brunocarvalhs.billet_registration.barcode_scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.billet_registration.databinding.FragmentBilletRegistrationBarcodeScannerBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.listeners.BarcodeScanListener
import br.com.brunocarvalhs.paguei.features.billet_registration.barcode_scanner.BilletRegistrationBarcodeScannerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalGetImage
@AndroidEntryPoint
class BilletRegistrationBarcodeScannerFragment :
    BaseFragment<FragmentBilletRegistrationBarcodeScannerBinding>(), BarcodeScanListener {

    private val viewModel: BilletRegistrationBarcodeScannerViewModel by viewModels()

    private val requestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) startCamera()
        }
    }

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val imageAnalysis by lazy { ImageAnalysis.Builder().build() }

    private val preview by lazy { Preview.Builder().build() }

    private val cameraSelector by lazy { CameraSelector.DEFAULT_BACK_CAMERA }


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentBilletRegistrationBarcodeScannerBinding =
        FragmentBilletRegistrationBarcodeScannerBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is BilletRegistrationBarcodeScannerViewState.Error -> this.showError(it.message)
                is BilletRegistrationBarcodeScannerViewState.Success -> this.displayData(it.code)
                BilletRegistrationBarcodeScannerViewState.Loading -> this.loading()
            }
        }
    }

    private fun displayData(barcode: String) {
        this.navigateToForm(barcode)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        requestPermission()
        binding.next.setOnClickListener { navigateToForm() }
    }

    override fun loading() {

    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
        viewModel.cameraProvider?.let { initCamera() }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            try {
                viewModel.cameraProvider = cameraProviderFuture.get()
                preview.setSurfaceProvider(binding.previewView.surfaceProvider)
                imageAnalysis.setAnalyzer(cameraExecutor, BarcodeAnalyzer(this))
                initCamera()
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, e.message ?: "")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onScanSuccess(barcode: String) {
        viewModel.barcodeScanner(barcode)
    }

    override fun onScanError(error: String) {
        showBottomSheet()
    }

    private fun showBottomSheet() {
        val action = BilletRegistrationBarcodeScannerFragmentDirections
            .actionBilletRegistrationBarcodeScannerFragmentToBilletRegistrationDialogFragment()
        findNavController().navigate(action)
    }

    private fun navigateToForm(barcode: String? = null) {
        try {
            val action = BilletRegistrationBarcodeScannerFragmentDirections
                .actionBilletRegistrationBarcodeScannerFragmentToBilletRegistrationFormFragment(
                    barcode
                )
            findNavController().navigate(action)
        } catch (error: Exception) {
            showBottomSheet()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.cameraProvider?.unbind(preview)
        viewModel.cameraProvider?.unbind(imageAnalysis)
    }

    private fun initCamera() {
        viewModel.cameraProvider?.unbindAll()
        viewModel.cameraProvider?.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
    }
}