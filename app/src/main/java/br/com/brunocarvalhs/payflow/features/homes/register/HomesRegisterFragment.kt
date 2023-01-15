package br.com.brunocarvalhs.payflow.features.homes.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentHomesRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomesRegisterFragment : BaseFragment<FragmentHomesRegisterBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomesRegisterBinding =
        FragmentHomesRegisterBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {

    }

    override fun loading() {

    }
}