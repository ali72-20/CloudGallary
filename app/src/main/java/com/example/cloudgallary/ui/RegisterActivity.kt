package com.example.cloudgallary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cloudgallary.Constants
import com.example.cloudgallary.databinding.ActivityRegisterBinding
import com.example.cloudgallary.user
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage


class RegisterActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityRegisterBinding
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        progressBar = viewBinding.registerProgbar
        setSupportActionBar(viewBinding.toolBar)
        initView()
        Register()
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()

    }
    private fun Register() {
        viewBinding.LoginBtn.setOnClickListener {
           ValidInput()
        }
    }

    private fun addTodateBase() {
        val PhoneNumber = viewBinding.inputFaild.text.toString()
        val userName = viewBinding.inputNameFaild.text.toString()
        val database =  FirebaseDatabase.getInstance().getReference(Constants.FireBasePath)
        database.child(PhoneNumber).setValue(userName).addOnCompleteListener {
            Toast.makeText(this,"Account Created successfully!!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun ValidInput(){
        if(viewBinding.inputFaild.text.toString().isBlank()){
            viewBinding.IdDil.error = "Please Enter your Phone Number"
            return
        }
        else if(viewBinding.inputFaild.text.toString().length != 11){
            viewBinding.IdDil.error = "Phone number must be 11 numbers"
            return
        }else{
            viewBinding.IdDil.error = null
        }
        if(viewBinding.inputNameFaild.text.toString().isBlank()){
            viewBinding.NameDil.error  = "Please Enter your user name"
            return
        }
        else if(viewBinding.inputNameFaild.text.toString().length < 3){
            viewBinding.NameDil.error = "user name must be grater than 3 letters"
            return
        }else{
            viewBinding.NameDil.error  = null
        }
        progressBar.visibility = View.VISIBLE
        val db = FirebaseDatabase.getInstance().getReference(Constants.FireBasePath)
        db.child(viewBinding.inputFaild.text.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressBar.visibility = View.INVISIBLE
                    // user exists in the database
                    val bulider: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
                    bulider.setTitle("User Already Exists").setMessage("You can LogIn")
                        .setPositiveButton("" +
                                "LogIn"){dialog,which->
                            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton("Close"){dialig,which->
                            dialig.dismiss()
                        }
                    val dialog : AlertDialog = bulider.create()
                    dialog.show()
                } else {
                    // user does not exist in the database
                    addTodateBase()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

}