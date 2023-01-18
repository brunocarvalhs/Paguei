package br.com.brunocarvalhs.paguei.features.billet_registration.barcode_scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.paguei.databinding.FragmentBilletRegistrationBarcodeScannerBinding
import br.com.brunocarvalhs.paguei.domain.listeners.BarcodeScanListener
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGetImage
@AndroidEntryPoint
class BilletRegistrationBarcodeScannerFragment :
    BaseFragment<FragmentBilletRegistrationBarcodeScannerBinding>(), BarcodeScanListener {

    private val viewModel: BilletRegistrationBarcodeScannerViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) startCamera()
            else {
                Toast.makeText(requireContext(), "Permissão negada", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

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
                viewModel.preview.setSurfaceProvider(binding.previewView.surfaceProvider)
                viewModel.imageAnalysis.setAnalyzer(viewModel.cameraExecutor, BarcodeAnalyzer(this))
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
        viewModel.cameraProvider?.unbind(viewModel.preview)
        viewModel.cameraProvider?.unbind(viewModel.imageAnalysis)
    }

    private fun initCamera() {
        viewModel.cameraProvider?.unbindAll()
        viewModel.cameraProvider?.bindToLifecycle(
            this,
            viewModel.cameraSelector,
            viewModel.preview,
            viewModel.imageAnalysis
        )
    }
}