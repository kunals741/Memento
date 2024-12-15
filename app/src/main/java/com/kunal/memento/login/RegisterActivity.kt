package com.kunal.memento.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.kunal.memento.MainActivity
import com.kunal.memento.MainApplication.Companion.auth
import com.kunal.memento.R
import com.kunal.memento.databinding.ActivityLoginBinding

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "RegisterActivity"

        fun startRegisterActivity(activity: Activity) {
            activity.startActivity(Intent(activity, RegisterActivity::class.java))
        }
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        tvTitle.text = getString(R.string.register)
        btnAdd.text = getString(R.string.register)
        tvRegister.isVisible = false
        btnAdd.setOnClickListener {
            if (!isValidEmail(etUserName.text.toString())) {
                tvUserNameError.isVisible = true
                tvUserNameError.text = getString(R.string.invalid_email)
            } else if(etPassword.text.toString().length<6){
                tvPasswordError.isVisible = true
                tvPasswordError.text = getString(R.string.password_should_be_atleast_6_characters)
            } else {
                binding.progressBar.isVisible = true
                binding.btnAdd.text = ""
                registerUser(etUserName.text.toString(), etPassword.text.toString())
            }
        }
        etUserName.doAfterTextChanged {
            tvUserNameError.isVisible = false
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.isVisible = false
                binding.btnAdd.text = getString(R.string.register)
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }
}