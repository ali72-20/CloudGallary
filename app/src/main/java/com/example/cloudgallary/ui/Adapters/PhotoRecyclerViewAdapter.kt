package com.example.cloudgallary.ui.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.cloudgallary.R
import com.example.cloudgallary.databinding.PhotpItemBinding
import com.example.cloudgallary.ui.MainActivity
import com.example.cloudgallary.ui.PhotoItem
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class PhotoRecyclerViewAdapter(var photo:List<ImageView>? = null): RecyclerView.Adapter<PhotoRecyclerViewAdapter.viewHolder>() {
    class viewHolder(var viewBinding:PhotpItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        val image : ImageView = viewBinding.itemImge
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = photo!![position]
        holder.viewBinding.itemImge.setImageResource(R.drawable.egypt)
        mListener?.let {
            it.onTaskIdReceived(currentItem)
        }
    }

    override fun getItemCount(): Int = photo?.size!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemBinding = PhotpItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(itemBinding)
    }
    var mListener: TaskIdListener? = null

    fun interface TaskIdListener {
        fun onTaskIdReceived(image: ImageView)
    }
}


