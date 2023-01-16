package br.com.brunocarvalhs.payflow.features.costs.reader_cost

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentCostReaderBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CostReaderFragment : BaseFragment<FragmentCostReaderBinding>() {

    private val viewModel: CostReaderViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCostReaderBinding =
        FragmentCostReaderBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
        binding.name.editText?.setText(viewModel.cost.name)
        binding.prompt.editText?.setText(viewModel.cost.prompt)
        binding.value.editText?.setText(viewModel.cost.value.toString())
        binding.barcode.editText?.setText(viewModel.cost.barCode)
        binding.barcode.setEndIconOnClickListener {
            viewModel.cost.barCode?.let { textCopyThenPost(it) }
        }
    }

    override fun loading() {

    }

    private fun textCopyThenPost(textCopied: String) {
        val clipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
        Toast.makeText(requireContext(), "Código copiado com sucesso.", Toast.LENGTH_SHORT)
            .show()
    }
}