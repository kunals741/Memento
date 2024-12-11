package com.kunal.memento

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kunal.memento.ViewExtensions.getParcelableCompat
import com.kunal.memento.adapter.NoteListAdapter
import com.kunal.memento.adapter.NoteListClickListener
import com.kunal.memento.constants.IntentKeyConstants
import com.kunal.memento.databinding.ActivityNoteListBinding
import com.kunal.memento.db.entity.Folders
import com.kunal.memento.db.entity.Note
import kotlinx.coroutines.launch

class NoteListActivity : AppCompatActivity(), NoteListClickListener {

    companion object {
        fun startNoteListActivity(
            folder: Folders,
            activity: AppCompatActivity
        ) {
            val intent = Intent(activity, NoteListActivity::class.java).apply {
                putExtra(IntentKeyConstants.FOLDER, folder)
            }
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityNoteListBinding

    private val folder: Folders by lazy {
        intent.getParcelableCompat(IntentKeyConstants.FOLDER)!!
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObservers()
        viewModel.getNotesForFolderId(folderId = folder.id)
    }

    private fun initView() = with(binding) {
        tvFolderName.text = folder.folderName
        ivDelete.setOnClickListener {
            viewModel.deleteFolderByID(folder.id)
            finish()
        }
        fabActionButton.setOnClickListener {
            val note = Note(
                folderId = folder.id,
                noteTitle = ""
            )
            viewModel.addNewNote(note)
            startNoteActivity(note)
        }
        rvNotes.adapter = NoteListAdapter(listener = this@NoteListActivity)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteList.collect {
                    getNotesAdapter().updateData(it)
                }
            }
        }
    }

    private fun startNoteActivity(note: Note) {
        NoteActivity.startNoteActivity(
            note,
            this@NoteListActivity
        )
    }

    private fun getNotesAdapter() = (binding.rvNotes.adapter as NoteListAdapter)

    override fun onNoteClick(note: Note) {
        startNoteActivity(note)
    }
}