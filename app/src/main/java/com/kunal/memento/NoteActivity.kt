package com.kunal.memento

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kunal.memento.constants.IntentKeyConstants
import com.kunal.memento.ViewExtensions.getParcelableCompat
import com.kunal.memento.databinding.ActivityNoteBinding
import com.kunal.memento.db.entity.Note

class NoteActivity : AppCompatActivity() {

    companion object {
        fun startNoteActivity(
            note: Note,
            activity: Activity
        ) {
            val intent = Intent(activity, NoteActivity::class.java).apply {
                putExtra(IntentKeyConstants.NOTE, note)
            }
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityNoteBinding

    private val viewModel: MainViewModel by viewModels()

    private val note: Note by lazy { intent.getParcelableCompat(IntentKeyConstants.NOTE)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        etNoteName.setText(note.noteTitle)
        etNoteText.setText(note.noteText)
        ivDelete.setOnClickListener {
            viewModel.deleteNote(note.id)
            finish()
        }
        onBackPressedDispatcher.addCallback(this@NoteActivity, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                note.noteTitle = binding.etNoteName.text.toString()
                note.noteText = binding.etNoteText.text.toString()
                viewModel.updateNote(note)
                finish()
            }
        })
    }

}