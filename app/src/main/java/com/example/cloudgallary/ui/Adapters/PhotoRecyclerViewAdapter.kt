package com.example.cloudgallary.ui.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloudgallary.ImageModel
import com.example.cloudgallary.databinding.PhotpItemBinding


class PhotoRecyclerViewAdapter(var photoList: ArrayList<ImageModel>, var context: Context): RecyclerView.Adapter<PhotoRecyclerViewAdapter.viewHolder>() {

    class viewHolder(var viewBinding:PhotpItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        val image : ImageView = viewBinding.itemImge
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        Glide.with(context)
            .load(photoList)
            .into(holder.image)
    }

    override fun getItemCount(): Int = photoList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemBinding = PhotpItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(itemBinding)
    }
    var mListener: TaskIdListener? = null

    fun interface TaskIdListener {
        fun onTaskIdReceived(image: ImageView)
    }
}


