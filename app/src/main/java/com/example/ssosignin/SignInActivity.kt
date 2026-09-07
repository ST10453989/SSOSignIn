package com.example.ssosignin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssosignin.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnContinueSso.setOnClickListener { attemptSsoSignIn() }
        binding.btnContinueGoogle.setOnClickListener { onSocialSignInSelected("Google") }
        binding.btnContinueMicrosoft.setOnClickListener { onSocialSignInSelected("Microsoft") }

        binding.emailEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                binding.emailInputLayout.error = null
            }
        })
    }

    private fun attemptSsoSignIn() {
        val email = binding.emailEditText.text?.toString()?.trim().orEmpty()

        when {
            email.isEmpty() -> {
                binding.emailInputLayout.error = getString(R.string.error_email_empty)
            }
            !isValidEmail(email) -> {
                binding.emailInputLayout.error = getString(R.string.error_email_invalid)
            }
            else -> {
                binding.emailInputLayout.error = null
                navigateToWelcomeScreen(email)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun navigateToWelcomeScreen(email: String) {
        val intent = Intent(this, WelcomeActivity::class.java).apply {
            putExtra(EXTRA_USER_EMAIL, email)
        }
        startActivity(intent)
        finish()
    }

    private fun onSocialSignInSelected(provider: String) {
        val message = when (provider) {
            "Google" -> getString(R.string.toast_google_selected)
            "Microsoft" -> getString(R.string.toast_microsoft_selected)
            else -> "$provider SSO selected"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_USER_EMAIL = "extra_user_email"
    }
}