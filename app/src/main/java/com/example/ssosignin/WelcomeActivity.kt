package com.example.ssosignin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssosignin.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displaySignedInEmail()

        binding.btnSignOut.setOnClickListener { signOut() }
    }

    private fun displaySignedInEmail() {
        val email = intent.getStringExtra(SignInActivity.EXTRA_USER_EMAIL).orEmpty()
        binding.textUserEmail.text = email
    }

    private fun signOut() {
        val intent = Intent(this, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}