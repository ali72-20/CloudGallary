package com.example.cloudgallary.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cloudgallary.Constants
import com.example.cloudgallary.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityLoginBinding
    lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        progressBar = viewBinding.linearProg
        StartSignIn()
        register()
    }

    private fun register() {
        viewBinding.registerTv.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private fun StartSignIn() {
        viewBinding.LoginBtn.setOnClickListener {
            ValidInput()
        }

    }

    private fun ValidInput() {
        if (viewBinding.inputFaild.text.toString().isBlank()) {
            viewBinding.IdDil.error = "Please Enter your Phone Number"
            return
        } else if(viewBinding.inputFaild.text.toString().length != 11) {
            viewBinding.IdDil.error = "Phone number must be 11 numbers"
            return
        }
        else{
            viewBinding.IdDil.error = null
        }
        progressBar.visibility = View.VISIBLE
        val db = FirebaseDatabase.getInstance().getReference(Constants.FireBasePath)
        db.child(viewBinding.inputFaild.text.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) = if (dataSnapshot.exists()) {
                // user exists in the database
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                val id = viewBinding.inputFaild.text.toString()
                intent.putExtra(Constants.PhoneNumber,id)
                startActivity(intent)
            } else {
                progressBar.visibility = View.INVISIBLE
                // user does not exist in the database
                val bulider:AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                bulider.setTitle("User Not found").setMessage("You can Register")
                    .setPositiveButton("Register"){dialog,which->
                        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton("Close"){dialog,which->
                        dialog.dismiss()
                    }
                val dialog : AlertDialog = bulider.create()
                dialog.show()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        }

    override fun onStop() {
        super.onStop()

    }
}

