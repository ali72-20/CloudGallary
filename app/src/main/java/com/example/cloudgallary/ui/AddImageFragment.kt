package com.example.cloudgallary.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.example.cloudgallary.R
import com.example.cloudgallary.databinding.FragmentAddImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddImageFragment :BottomSheetDialogFragment() {
   var imgeuri : Uri?= null
    lateinit var viewBinding: FragmentAddImageBinding
    lateinit var id : String
    val REQUEST_CODE = 1
    var cnt :Int = 0
    var dbRef = FirebaseDatabase.getInstance().getReference("Images")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddImageBinding.inflate(inflater,container,false)
        val data = arguments
        id = data!!.getString("ID").toString()
        viewBinding.upload.text = "Upload"
        dbRef.push().key!!
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.cameraImg.setOnClickListener {
            val intent =
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(activity?.packageManager!!) != null) {
                val imageFile = createImageFile()
                imgeuri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.homeactivity.fileprovider",
                    imageFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgeuri)
                activity?.startActivityForResult(intent, REQUEST_CODE)
            }
        }
        viewBinding.galleryImg.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, 100)
        }
        viewBinding.upload.setOnClickListener {
            uploadPhoto()
        }
    }

    private fun uploadPhoto() {
        if(imgeuri == null){
            Snackbar.make(viewBinding.upload,"Uploaded failed!! Please select a photo!",Snackbar.LENGTH_SHORT).show()
            return
        }
        viewBinding.upload.text = null
        viewBinding.upload.background = R.color.white.toDrawable()
        viewBinding.uploadProg.visibility = View.VISIBLE
        Log.e("folderName",id)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val fileName = imgeuri!!.lastPathSegment.toString()
        val file = storageRef.child(id).child((fileName))
        file.putFile(imgeuri!!).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                dbRef.child(id).push().child("userImage").setValue(imageUrl).addOnCompleteListener {

                }
            }
            Toast.makeText(requireContext(),"Uploaded Success",Toast.LENGTH_SHORT).show()
            onCompleteUpload?.let {
                it.onComplete()
            }
            dismiss()
        }
    }

     var onCompleteUpload : OnCompleteUpload?=null
   fun interface OnCompleteUpload{
       fun onComplete()
   }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null) {
            val imageUri : Uri = data.data!!
            imgeuri = imageUri
            viewBinding.selectedImage.setImageURI(imageUri)
        }
    }

    private fun createImageFile(): File {
        val storageDir: File? = activity?.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            "JPEG_${simpleDateFormat.format(Date())}_",
            ".jpg",
            storageDir
        )
    }
    private val simpleDateFormat = SimpleDateFormat(
        "yyyyMMdd_HHmmss",
        Locale.US
    )
}