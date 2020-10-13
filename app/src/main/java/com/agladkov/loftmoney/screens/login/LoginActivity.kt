package com.agladkov.loftmoney.screens.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.agladkov.loftmoney.LoftApp
import com.agladkov.loftmoney.R
import com.agladkov.loftmoney.screens.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        configureViews()
        configureViewModel()
    }

    private fun configureViews() {
        val loginEnterView = findViewById<AppCompatButton>(R.id.loginEnterView)
        loginEnterView.setOnClickListener { v: View? -> loginViewModel?.makeLogin((application as? LoftApp)?.authApi) }
    }

    private fun configureViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel?.messageString?.observe(this, Observer { error: String? ->
            if (error?.isNotBlank() == false) {
                Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
            }
        })
        loginViewModel?.authToken?.observe(this, Observer { authToken: String? ->
            if (!TextUtils.isEmpty(authToken)) {
                val sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0)
                sharedPreferences.edit().putString(LoftApp.AUTH_KEY, authToken).apply()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}