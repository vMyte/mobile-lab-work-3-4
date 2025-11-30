package com.example.firstlab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.R
import com.example.firstlab.data.WordList

class WordListAdapter(
    private val onClick: (WordList) -> Unit
) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    private val lists = mutableListOf<WordList>()

    fun setData(data: List<WordList>) {
        lists.clear()
        lists.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount(): Int = lists.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.tvListName)

        fun bind(list: WordList) {
            nameText.text = list.name
            itemView.setOnClickListener { onClick(list) }
        }
    }
}
