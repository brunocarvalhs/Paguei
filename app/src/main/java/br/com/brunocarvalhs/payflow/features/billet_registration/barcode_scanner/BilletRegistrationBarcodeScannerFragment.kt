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

    private val imageAnalysis = ImageAnalysis.Builder().build()

    private val preview = Preview.Builder().build()

    private var cameraProvider: ProcessCameraProvider? = null

    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

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
        cameraProvider?.let { initCamera() }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            preview.setSurfaceProvider(binding.previewView.surfaceProvider)

            imageAnalysis.setAnalyzer(cameraExecutor, BarcodeAnalyzer(this))

            try {
                initCamera()
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, e.message ?: "")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onScanSuccess(barcode: String) {
        this.navigateToForm(barcode)
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
        val action = BilletRegistrationBarcodeScannerFragmentDirections
            .actionBilletRegistrationBarcodeScannerFragmentToBilletRegistrationFormFragment(barcode)
        findNavController().navigate(action)
    }

    override fun onStop() {
        super.onStop()
        cameraProvider?.unbind(preview)
        cameraProvider?.unbind(imageAnalysis)
    }

    private fun initCamera() {
        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
    }
}