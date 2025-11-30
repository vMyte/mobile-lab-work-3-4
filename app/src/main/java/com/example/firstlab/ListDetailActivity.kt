package com.example.firstlab

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.adapter.WordAdapter
import com.example.firstlab.data.DataRepository

class ListDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIST_ID = "list_id"
    }

    private var listId: Int = -1
    private lateinit var wordListView: RecyclerView
    private lateinit var wordAdapter: WordAdapter
    private lateinit var nameText: TextView
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        listId = intent.getIntExtra(EXTRA_LIST_ID, -1)
        if (listId == -1) {
            finish()
            return
        }

        initViews()
        initWordList()
        initButtons()
        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun initViews() {
        nameText = findViewById(R.id.tvListName)
        emptyText = findViewById(R.id.tvEmptyList)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun initWordList() {
        wordListView = findViewById(R.id.recyclerViewWords)
        wordAdapter = WordAdapter()
        wordListView.layoutManager = LinearLayoutManager(this)
        wordListView.adapter = wordAdapter
    }

    private fun initButtons() {
        findViewById<Button>(R.id.btnAddWord).setOnClickListener {
            showAddWordDialog()
        }

        findViewById<Button>(R.id.btnStartTraining).setOnClickListener {
            val words = DataRepository.getWords(listId)
            if (words.isEmpty()) {
                Toast.makeText(this, "Добавьте слова для тренировки", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, TrainingActivity::class.java)
                intent.putExtra(TrainingActivity.EXTRA_LIST_ID, listId)
                startActivity(intent)
            }
        }
    }

    private fun showAddWordDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_word)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val wordInput = dialog.findViewById<EditText>(R.id.etEnglishWord)
        val translationInput = dialog.findViewById<EditText>(R.id.etTranslation)
        val saveBtn = dialog.findViewById<Button>(R.id.btnSaveWord)

        saveBtn.setOnClickListener {
            val word = wordInput.text.toString().trim()
            val translation = translationInput.text.toString().trim()

            if (word.isNotEmpty() && translation.isNotEmpty()) {
                DataRepository.createWord(listId, word, translation)
                refresh()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun refresh() {
        val list = DataRepository.getListById(listId)
        nameText.text = list?.name ?: ""

        val words = DataRepository.getWords(listId)
        wordAdapter.setData(words)

        if (words.isEmpty()) {
            wordListView.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        } else {
            wordListView.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
        }
    }
}
