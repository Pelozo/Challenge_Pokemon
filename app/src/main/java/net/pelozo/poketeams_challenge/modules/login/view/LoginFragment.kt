package net.pelozo.poketeams_challenge.modules.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import net.pelozo.poketeams_challenge.MainActivity
import net.pelozo.poketeams_challenge.base.BaseFragment
import net.pelozo.poketeams_challenge.databinding.FragmentLoginBinding
import net.pelozo.poketeams_challenge.modules.login.viewModel.LoginViewModel
import net.pelozo.poketeams_challenge.withViewModel

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) { result: FirebaseAuthUIAuthenticationResult ->
            onSignInResult(result)
        }

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initView() {
        super.initView()
        with(binding) {
            btGoogleSignIn.setOnClickListener {
                login(AuthUI.IdpConfig.GoogleBuilder().build())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(FirebaseAuth.getInstance().currentUser != null){
            moveToRegionList()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == -1) {
            moveToRegionList()
        }
    }

    private fun login(build: AuthUI.IdpConfig): Boolean {
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    build
                )
            )
            .build()
        signInLauncher.launch(intent)
        return true
    }


    override fun getViewModel(): LoginViewModel = withViewModel {

    }

    private fun moveToRegionList() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToRegionListFragment()
        )
    }

}