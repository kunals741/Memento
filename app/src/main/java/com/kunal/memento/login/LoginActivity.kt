package com.kunal.memento.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.kunal.memento.MainActivity
import com.kunal.memento.MainApplication.Companion.auth
import com.kunal.memento.R
import com.kunal.memento.databinding.ActivityLoginBinding
import com.kunal.memento.login.RegisterActivity.Companion.TAG

class LoginActivity : AppCompatActivity() {

    companion object {
        fun startLoginActivity(activity: Activity) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        btnAdd.setOnClickListener {
            if (isValidEmail(etUserName.text.toString())) {
                binding.progressBar.isVisible = true
                binding.btnAdd.text = ""
                registerUser(etUserName.text.toString(), etPassword.text.toString())
            } else {
                tvUserNameError.isVisible = true
                tvUserNameError.text = getString(R.string.invalid_email)
            }
        }

        tvRegister.setOnClickListener {
            RegisterActivity.startRegisterActivity(this@LoginActivity)
            finish()
        }

        etPassword.doAfterTextChanged {
            tvPasswordError.isVisible = false
        }
        etUserName.doAfterTextChanged {
            tvUserNameError.isVisible = false
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.isVisible = false
                binding.btnAdd.text = getString(R.string.login)
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    binding.tvUserNameError.isVisible = true
                    binding.tvUserNameError.text = getString(R.string.invalid_email)
                    binding.tvPasswordError.isVisible = true
                    binding.tvPasswordError.text = getString(R.string.invalid_password)
                }
            }

    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }
}