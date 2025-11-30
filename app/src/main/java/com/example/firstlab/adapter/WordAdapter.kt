package com.example.firstlab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.R
import com.example.firstlab.data.Word

class WordAdapter : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private val words = mutableListOf<Word>()

    fun setData(data: List<Word>) {
        words.clear()
        words.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int = words.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordText: TextView = itemView.findViewById(R.id.tvWord)

        fun bind(word: Word) {
            wordText.text = "${word.englishWord} (${word.russianTranslation})"
        }
    }
}
