package com.example.cloudgallary.ui


import android.content.Intent

import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudgallary.Constants

import com.example.cloudgallary.databinding.ActivityMainBinding
import com.example.cloudgallary.ui.Adapters.PhotoRecyclerViewAdapter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityMainBinding
    lateinit var recView : RecyclerView
    lateinit var id : String
    lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        id = intent.getStringExtra(Constants.PhoneNumber)?:""
        viewBinding.homeContent.userId.text = id
        retrieveImages()
        addPhoto()
        logout()
    }

    private fun retrieveImages() {
        recView = viewBinding.recManager
        recView.layoutManager = LinearLayoutManager(this)
        dbRef = FirebaseDatabase.getInstance().getReference("Images").child(id)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val newList : MutableList<ImageDetails> = mutableListOf()
                    for (dataSnapShot in snapshot.children) {
                        val image = dataSnapShot.getValue(ImageDetails::class.java)
                        newList.add(image!!)
                    }
                    recView.adapter = PhotoRecyclerViewAdapter(newList, this@MainActivity)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }





    private fun logout() {
        viewBinding.homeContent.toolBar.setOnMenuItemClickListener {
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Logout").setMessage("Are you sure to logout").setPositiveButton("Logout") { dialog, which ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Close"){dialog,which->

             }
            builder.create()
            builder.show()
            true
        }
    }

    private fun addPhoto() {
        viewBinding.homeContent.extendedFab.setOnClickListener{
            val addImageFragment = AddImageFragment()
            val bundle  = Bundle()
            bundle.putString("ID",id)
            addImageFragment.arguments = bundle
            addImageFragment.show(supportFragmentManager,null)
        }
    }
}
