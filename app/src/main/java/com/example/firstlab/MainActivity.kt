package com.example.firstlab

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.adapter.WordListAdapter
import com.example.firstlab.data.DataRepository

class MainActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    private lateinit var listAdapter: WordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()
        initAddButton()
    }

    override fun onResume() {
        super.onResume()
        refreshLists()
    }

    private fun initList() {
        listView = findViewById(R.id.recyclerViewLists)
        listAdapter = WordListAdapter { list ->
            val intent = Intent(this, ListDetailActivity::class.java)
            intent.putExtra(ListDetailActivity.EXTRA_LIST_ID, list.id)
            startActivity(intent)
        }
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = listAdapter
    }

    private fun initAddButton() {
        findViewById<Button>(R.id.btnAddList).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val nameInput = dialog.findViewById<EditText>(R.id.etListName)
        val saveBtn = dialog.findViewById<Button>(R.id.btnSaveList)

        saveBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            if (name.isNotEmpty()) {
                DataRepository.createList(name)
                refreshLists()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Введите название списка", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun refreshLists() {
        listAdapter.setData(DataRepository.getLists())
    }
}
