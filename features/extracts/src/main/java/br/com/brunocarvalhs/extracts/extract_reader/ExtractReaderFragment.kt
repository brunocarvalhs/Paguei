package br.com.brunocarvalhs.extracts.extract_reader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.extracts.databinding.FragmentExtractReaderBinding
import br.com.brunocarvalhs.paguei.features.costs.extract_reader.ExtractReaderViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExtractReaderFragment : BaseFragment<FragmentExtractReaderBinding>() {

    private val viewModel: ExtractReaderViewModel by viewModels()

    private val clipboardManager by lazy {
        requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentExtractReaderBinding =
        FragmentExtractReaderBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ExtractReaderViewState.Error -> this.showError(it.error)
                ExtractReaderViewState.Loading -> this.loading()
                is ExtractReaderViewState.Success -> this.navigateToCosts()
            }
        }
    }

    private fun navigateToCosts() {
        findNavController().popBackStack()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.paymentVoucherUri.setEndIconOnClickListener {
            textCopyThenPost(viewModel.cost.paymentVoucher.toString())
        }
        binding.open.setOnClickListener {
            viewModel.openFile(requireContext(), viewModel.cost.paymentVoucher)
        }
    }

    override fun loading() {

    }

    private fun textCopyThenPost(textCopied: String) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
        Toast.makeText(requireContext(), "C??digo copiado com sucesso.", Toast.LENGTH_SHORT).show()
    }
}