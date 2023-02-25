package br.com.brunocarvalhs.profile.abort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.profile.R
import br.com.brunocarvalhs.profile.databinding.FragmentAbortBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbortFragment : BaseFragment<FragmentAbortBinding>() {

    private val viewModel: AbortViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentAbortBinding =
        FragmentAbortBinding.inflate(inflater, container, attachToParent)

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        visibilityToolbar(true)
        binding.version.text = requireContext().getString(R.string.version_app)
    }

    override fun loading() {

    }

    override fun viewObservation() {

    }

}