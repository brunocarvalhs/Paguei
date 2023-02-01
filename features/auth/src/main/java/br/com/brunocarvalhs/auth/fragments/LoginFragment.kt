package br.com.brunocarvalhs.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.auth.databinding.FragmentLoginBinding
import br.com.brunocarvalhs.auth.states.LoginViewState
import br.com.brunocarvalhs.auth.viewmodels.LoginViewModel
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.paguei.commons.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.common.Scopes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var navigation: Navigation
    private val viewModel: LoginViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
        viewModel.onSignInResult()
    }

    private val providers by lazy {
        arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build(),
            AuthUI.IdpConfig.GoogleBuilder().setScopes(listOf(Scopes.PROFILE)).build()
        )
    }

    private val signInIntent by lazy {
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.Theme_Paguei)
            .setAvailableProviders(providers)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginViewState.Loading -> loading()
                LoginViewState.Success -> navigateToHome()
                is LoginViewState.Error -> showError(state.message)
            }
        }
    }

    private fun navigateToHome() {
        val request = navigation.navigateToCostsRegister()
        findNavController().navigate(request)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        binding.button.setOnClickListener {
            signInLauncher.launch(signInIntent)
        }
    }

    override fun loading() {

    }

    override fun onStart() {
        super.onStart()
        viewModel.onSession()
    }
}
