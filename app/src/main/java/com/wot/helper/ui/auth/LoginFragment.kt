package com.wot.helper.ui.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent
import com.google.android.gms.common.api.ApiException
import com.wot.helper.common.Constants
import com.wot.helper.databinding.FragmentLoginBinding
import com.wot.helper.domain.models.use_case.auth.Response
import com.wot.helper.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.browser.customtabs.CustomTabsIntent
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var signInIntent: Intent

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<LoginViewModel>()

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast
    private lateinit var progressBar: ContentLoadingProgressBar

    private var email: String = ""
    private var password: String = ""

    companion object {
        private const val WARGAMING_APP_ID = "ace14516f4be72cde04425adca560339"
        private const val WARGAMING_REDIRECT_URI = "http://10.0.2.2:5001/wot-helper-edc0c/us-central1/handleWargamingRedirect"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkUserState()
        super.onViewCreated(view, savedInstanceState)

        initGoogleResultLauncher()

        binding.apply {
            this@LoginFragment.progressBar = this.progressBar

            btnLogin.setOnClickListener {
                validateDataAndLogin()
            }
            btnCreateAccount.setOnClickListener {
                navigateToRegister()
            }
            btnForgotPassword.setOnClickListener {
                navigateToForgotPassword()
            }
            btnGoogleSignIn.setOnClickListener {
                launchGoogleSignInIntent()
            }
            btnWargamingSignIn.setOnClickListener {
                launchWargamingSignInIntent()
            }
            doubleTapToExit()
        }

        // Handle deep link
        arguments?.getString("deep_link_uri")?.let { uriString ->
            val uri = uriString.toUri()
            if (uri.scheme == "wot-helper" && uri.host == "callback") {
                val customToken = uri.getQueryParameter("custom_token")
                if (!customToken.isNullOrEmpty()) {
                    progressBar.show()
                    firebaseSignInWithWargaming(customToken)
                } else {
                    progressBar.hide()
                    Toast.makeText(requireContext(), "No custom token received", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun doubleTapToExit() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + Constants.EXIT_TIME > System.currentTimeMillis()) {
                        backPressedToast.cancel()
                        requireActivity().finish()
                        return
                    } else {
                        backPressedToast =
                            Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_LONG)
                        backPressedToast.show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })
    }

    private fun launchGoogleSignInIntent() {
        progressBar.show()
        resultLauncher.launch(signInIntent)
    }

    private fun initGoogleResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = getSignedInAccountFromIntent(result.data)
                    try {
                        val googleSignInAccount = task.getResult(ApiException::class.java)
                        googleSignInAccount?.apply {
                            idToken?.let { idToken ->
                                firebaseSignInWithGoogle(idToken)
                            }
                        }
                    } catch (e: ApiException) {
                        progressBar.hide()
                        Toast.makeText(requireContext(), "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    progressBar.hide()
                }
            }
    }

    private fun launchWargamingSignInIntent() {
        progressBar.show()
        val url = "https://api.worldoftanks.eu/wot/auth/login/?" +
                "application_id=$WARGAMING_APP_ID&" +
                "redirect_uri=${Uri.encode(WARGAMING_REDIRECT_URI)}"
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(requireContext(), url.toUri())
    }

    private fun firebaseSignInWithGoogle(idToken: String) {
        viewModel.firebaseSignInWithGoogle(idToken).observe(viewLifecycleOwner) { response: Response<Boolean> ->
            when (response) {
                is Response.Success -> {
                    progressBar.hide()
                }
                is Response.Failure -> {
                    progressBar.hide()
                    Toast.makeText(requireContext(), "Google Sign-In error: ${response.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firebaseSignInWithWargaming(token: String) {
        viewModel.firebaseSignInWithWargaming(token).observe(viewLifecycleOwner) { response: Response<Boolean> ->
            when (response) {
                is Response.Success -> {
                    progressBar.hide()
                }
                is Response.Failure -> {
                    progressBar.hide()
                    Toast.makeText(requireContext(), "Wargaming Sign-In error: ${response.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkUserState() {
        viewModel.authState.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                navigateToHome()
            }
        }
    }

    private fun validateDataAndLogin() {
        val context = requireContext()
        binding.apply {
            email = inputEmail.text.toString()
            password = inputPassword.text.toString()

            val result = viewModel.validateLoginForm(
                email = email,
                password = password
            )

            layoutEmail.helperText = result.emailError?.asString(context)
            layoutPassword.helperText = result.passwordError?.asString(context)
            if (result.hasNoError) {
                loginWithEmailAndPassword()
            }
        }
    }

    private fun loginWithEmailAndPassword() {
        progressBar.show()
        viewModel.firebaseSignInWithEmail(email, password).observe(viewLifecycleOwner) { response: Response<Boolean> ->
            when (response) {
                is Response.Success -> {
                    progressBar.hide()
                }
                is Response.Failure -> {
                    progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Invalid credentials. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun navigateToHome() {
        val navHome = LoginFragmentDirections.actionGlobalHomePageFragment()
        findNavController().navigate(navHome)
    }

    private fun navigateToForgotPassword() {
        val navForgotPassword = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
        findNavController().navigate(navForgotPassword)
    }

    private fun navigateToRegister() {
        val navRegister = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(navRegister)
    }

    override fun onPause() {
        super.onPause()
        progressBar.hide()
    }
}