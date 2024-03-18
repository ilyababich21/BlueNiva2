package com.example.blueniva2.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blueniva2.model.FileItem
import com.example.blueniva2.R
import com.example.blueniva2.databinding.FileItemBinding

class FileAdapter(private val listener: Listener) : RecyclerView.Adapter<FileAdapter.FileHolder>() {

    var fileList:List<FileItem> = ArrayList()


    class FileHolder(item: View,private val listener: Listener) : RecyclerView.ViewHolder(item) {
        val binding = FileItemBinding.bind(item)
        private var item1: FileItem? = null



        init {
            itemView.setOnClickListener{
                item1?.let { it1 -> listener.onClick(it1) }
            }
        }

        fun bind(fileItem: FileItem) = with(binding) {
            item1 = fileItem
            tvName.text = fileItem.name
            tvDesc.text = fileItem.description
            tvImage.setImageResource(fileItem.imageId)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)
        return FileHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: FileHolder, position: Int) {
        return holder.bind(fileList[position])
    }


//    fun addFile(file: FileItem){
//        fileList.add(file)
//        notifyDataSetChanged()
//    }

//    fun clearFile(){
//        fileList.clear()
//    }

    fun refreshFiles(files:List<FileItem>){
        fileList = files
        notifyDataSetChanged()

    }

    interface Listener{
        fun onClick(item: FileItem)
    }

}
