package br.com.brunocarvalhs.payflow.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(true)
    }

    override fun loading() {

    }
}