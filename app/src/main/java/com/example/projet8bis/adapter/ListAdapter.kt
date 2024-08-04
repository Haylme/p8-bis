package com.example.projet8bis.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet8bis.model.Content
import com.example.projet8bis.model.SimpleResponse
import com.example.projet8bis.viewholder.ListViewHolder
import com.example.techradar.databinding.ItemBinding

class ListAdapter(
    private var list: List<Content>,
    private val onItemClick: (Content) -> Unit
) : RecyclerView.Adapter<ListViewHolder>() {


    fun updateList(newList: SimpleResponse<List<Content?>>) {
        val filteredList = newList.data?.filterNotNull() ?: emptyList()
        list = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding) {
            onItemClick(list[it])
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
