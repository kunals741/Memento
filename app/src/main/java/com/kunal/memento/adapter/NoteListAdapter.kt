package com.kunal.memento.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.memento.databinding.ItemNoteBinding
import com.kunal.memento.db.entity.Note

class NoteListAdapter(
    private val noteList: MutableList<Note> = mutableListOf(),
    private val listener: NoteListClickListener
) :
    RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NoteListViewHolder(
        ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: NoteListAdapter.NoteListViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount() = noteList.size

    fun updateData(newList: List<Note>) {
        val diffUtilResult = DiffUtil.calculateDiff(
            NoteListDiffUtil(
                noteList,
                newList
            )
        )
        noteList.clear()
        noteList.addAll(newList)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class NoteListViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) = with(binding) {
            tvNoteTitle.text = note.noteTitle
            tvNoteContent.text = note.noteText
            root.setOnClickListener {
                listener.onNoteClick(note)
            }
        }
    }


    inner class NoteListDiffUtil(
        private val oldList: List<Note>,
        private val newList: List<Note>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].noteTitle == newList[newItemPosition].noteTitle
                    && oldList[oldItemPosition].noteText == newList[newItemPosition].noteText)
        }
    }
}

interface NoteListClickListener {
    fun onNoteClick(note: Note)
}