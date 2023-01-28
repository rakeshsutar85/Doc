package com.gmail.rakeshsutar85.doc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.gmail.rakeshsutar85.doc.api.RetrofitClient
import com.gmail.rakeshsutar85.doc.databinding.ActivityRegistrationBinding
import com.gmail.rakeshsutar85.doc.models.DefaultResponse
import com.gmail.rakeshsutar85.doc.storage.SharedPrefManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RegistrationClickHere.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.registerButton.setOnClickListener {

            val email = binding.registerEmail.text.toString().trim()
            val name = binding.registerName.text.toString().trim()
            val mobile = binding.registerMobile.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            val confirmPassword = binding.registerRenamePassword.text.toString().trim()


            if(email.isEmpty()){
                binding.registerEmail.error = "Email required"
                binding.registerEmail.requestFocus()
                return@setOnClickListener
            }


            if(password.isEmpty()){
                binding.registerPassword.error = "Password required"
                binding.registerPassword.requestFocus()
                return@setOnClickListener
            }

            if(password!=confirmPassword){
                binding.registerRenamePassword.error = "Confirm Password must be match with password"
                binding.registerRenamePassword.requestFocus()
                return@setOnClickListener
            }


            if(name.isEmpty()){
                binding.registerName.error = "Name required"
                binding.registerName.requestFocus()
                return@setOnClickListener
            }

            if(mobile.isEmpty()){
                binding.registerMobile.error = "Mobile required"
                binding.registerMobile.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.createUser(name, email, mobile, password)
                .enqueue(object: Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if (response.body()?.message == "success") {
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        if (response.body()?.message == "failure") {
                            Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                        }                    }

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

