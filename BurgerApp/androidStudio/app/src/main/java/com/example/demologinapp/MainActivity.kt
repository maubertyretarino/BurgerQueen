package com.example.demologinapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demologinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for login button
        binding.loginButton.setOnClickListener(View.OnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            // Check if the credentials are correct
            if (username == "user" && password == "2352120") {
                // Show a successful login message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                // Redirect to burgerHome activity
                val intent = Intent(this, burgerHome::class.java)
                startActivity(intent)
            } else {
                // Show a failed login message
                Toast.makeText(this, "Invalid credentials, try again!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
