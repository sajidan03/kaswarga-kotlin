package com.example.kaswarga_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val daftar = findViewById<TextView>(R.id.daftar)
        daftar.setOnClickListener {
//            startActivity(Intent(applicationContext, DaftarAkun::class.java))
        }
        val token = SessionManager.getToken(applicationContext).toString()
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<AppCompatButton>(R.id.login)
//        val progressBar = findViewById<ProgressBar>(R.id.loader)
        button.setOnClickListener {
            if (username.text.length == 0 || password.text.length == 0 ){
                Toast.makeText(applicationContext, "Harap isi kolom username dan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
//                progressBar.visibility = View.VISIBLE
                RetrofitClient.getInstance(application, token).login(username.text.toString(), password.text.toString()).enqueue(
                    object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            Log.d("Respon login", "$response")
                            if (response.isSuccessful){
//                                progressBar.visibility = View.GONE
                                Log.d("Respon login", "$response")
                                val respon = response.body()?.message
                                val token = response.body()?.token.toString()
                                SessionManager.putToken(applicationContext, token)
                                Toast.makeText(applicationContext, "$respon", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                            }
                        }
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Log.d("Error respon", "$t")
//                            progressBar.visibility = View.GONE
                        }
                    }
                )
            }
        }
//        progressBar.visibility = View.GONE

    }
}