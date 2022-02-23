package com.raytalktech.gleamy.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.raytalktech.gleamy.Utils.ManagePermission
import com.raytalktech.gleamy.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    //Init Account
    private var userPass: String = "12345"

    //Init Properties
    private lateinit var binding: ActivityLoginBinding
    private lateinit var managePermission: ManagePermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etEmail.doOnTextChanged { text, _, _, _ -> validate(text) }
        binding.etPassword.doOnTextChanged { text, _, _, _ -> validate(text) }
        binding.tvForgotPassword.setOnClickListener { showMessage(String.format("username dan pass = %s", userPass)) }


        val list = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        managePermission = ManagePermission(this, list, 1)

        binding.btnLogin.setOnClickListener {
            if (managePermission.checkPermissions()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun validate(text: CharSequence?) {
        var isMatch = false

        if (text != null) {
            if (text.toString() == userPass) isMatch = true
        } else {
            showMessage(String.format("username dan pass = %s", userPass))
        }

        binding.btnLogin.isEnabled = isMatch
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}