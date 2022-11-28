package com.gmail.rakeshsutar85.doc

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gmail.rakeshsutar85.doc.api.RetrofitClient
import com.gmail.rakeshsutar85.doc.databinding.ActivityLoginBinding
import com.gmail.rakeshsutar85.doc.models.DefaultResponse
import com.gmail.rakeshsutar85.doc.models.LoginResponse
import com.gmail.rakeshsutar85.doc.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginClickHere.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {

            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if(email.isEmpty()){
                binding.loginEmail.error = "Email required"
                binding.loginEmail.requestFocus()
                return@setOnClickListener
            }


            if(password.isEmpty()){
                binding.loginPassword.error = "Password required"
                binding.loginPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(email, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (!response.body()?.error!!) {
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()

                        }
                        else {
                            Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                        }

                    }

                })

        }

    }
    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

}
