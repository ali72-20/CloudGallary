package com.example.cloudgallary.ui.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloudgallary.R
import com.example.cloudgallary.databinding.PhotpItemBinding
import com.example.cloudgallary.ui.ImageDetails
import com.google.firebase.storage.FileDownloadTask


class PhotoRecyclerViewAdapter(var photoList: MutableList<ImageDetails>, private val context: Context): RecyclerView.Adapter<PhotoRecyclerViewAdapter.viewHolder>() {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.item_imge)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Glide.with(context).load(photoList[position].userImage).into(holder.image)
    }

    override fun getItemCount(): Int = photoList.size


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photp_item,parent,false)
        return viewHolder(itemView)
    }

}


