package com.example.cloudgallary.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cloudgallary.Constants
import com.example.cloudgallary.R
import com.example.cloudgallary.databinding.ActivityMainBinding
import com.example.cloudgallary.ui.Adapters.PhotoRecyclerViewAdapter
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityMainBinding
    lateinit var id : String
    var curFile: Uri? = null
    val imageRef = Firebase.storage.reference
    lateinit var adapter: PhotoRecyclerViewAdapter
    lateinit var photoList :ArrayList<ImageView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        addPhoto()
        id = intent.getStringExtra(Constants.PhoneNumber)?:""
        viewBinding.homeContent.userId.text = id
//        initViews()
        addPhoto()

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

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val menuInflater : MenuInflater = menuInflater
//        menuInflater.inflate(R.menu.drop_list,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.logout_tv){
//            val bulider : AlertDialog.Builder = AlertDialog.Builder(this)
//            bulider.setTitle("Logout").setMessage("Are you sure to logout").setPositiveButton("Logout"){dialog,which->
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//            }
//            bulider.create()
//            bulider.show()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun initViews() {
        photoList = arrayListOf()
        adapter = PhotoRecyclerViewAdapter(photoList)
        viewBinding.recManager.adapter = adapter
    }



}