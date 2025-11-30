package com.example.firstlab

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.firstlab.data.DataRepository
import com.example.firstlab.data.Word

class TrainingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIST_ID = "list_id"
    }

    private var listId: Int = -1
    private lateinit var words: List<Word>
    private var currentIndex = 0
    private var showingEnglish = true

    private lateinit var nameText: TextView
    private lateinit var card: CardView
    private lateinit var cardText: TextView
    private lateinit var finishBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        listId = intent.getIntExtra(EXTRA_LIST_ID, -1)
        if (listId == -1) {
            finish()
            return
        }

        initViews()
        loadWords()
    }

    private fun initViews() {
        nameText = findViewById(R.id.tvListName)
        card = findViewById(R.id.cardFlashcard)
        cardText = findViewById(R.id.tvFlashcardText)
        finishBtn = findViewById(R.id.btnFinishTraining)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        card.setOnClickListener {
            flip()
        }

        finishBtn.setOnClickListener {
            finish()
        }
    }

    private fun loadWords() {
        val list = DataRepository.getListById(listId)
        nameText.text = list?.name ?: ""

        words = DataRepository.getWords(listId).shuffled()

        if (words.isNotEmpty()) {
            displayWord()
        }
    }

    private fun displayWord() {
        if (currentIndex < words.size) {
            showingEnglish = true
            cardText.text = words[currentIndex].englishWord
        }
    }

    private fun flip() {
        card.animate()
            .scaleX(0f)
            .setDuration(150)
            .withEndAction {
                if (showingEnglish) {
                    cardText.text = words[currentIndex].russianTranslation
                    showingEnglish = false
                } else {
                    currentIndex = (currentIndex + 1) % words.size
                    cardText.text = words[currentIndex].englishWord
                    showingEnglish = true
                }

                card.animate()
                    .scaleX(1f)
                    .setDuration(150)
                    .start()
            }
            .start()
    }
}
