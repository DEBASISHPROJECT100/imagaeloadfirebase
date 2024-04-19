package com.example.imageloadtocloud

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloadtocloud.databinding.EachItemBinding
import com.squareup.picasso.Picasso

class Imageadapter (private var mList:List<String>)
    :RecyclerView.Adapter<Imageadapter.imageviewholder>(){
    inner class imageviewholder(var binding :EachItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageviewholder {
       val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return imageviewholder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: imageviewholder, position: Int) {
        with(holder.binding){
            with (mList[position]){
                Picasso.get().load(this).into(Imageview)
            }
        }
    }
}