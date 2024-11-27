package com.kunal.memento.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.memento.databinding.ItemFolderCardBinding
import com.kunal.memento.db.entity.Folder

class FolderAdapter(private var folderList: MutableList<Folder> = mutableListOf()) :
    RecyclerView.Adapter<FolderAdapter.FolderAdapterViewHolder>() {

    inner class FolderAdapterViewHolder(private val itemBinding: ItemFolderCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(folder: Folder) {
            itemBinding.tvTitle.text = folder.folderName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderAdapterViewHolder {
        return FolderAdapterViewHolder(
            ItemFolderCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = folderList.size

    override fun onBindViewHolder(holder: FolderAdapterViewHolder, position: Int) {
        holder.bind(folderList[position])
    }

    fun updateData(newList: List<Folder>) {
        val diffResult = DiffUtil.calculateDiff(FolderAdapterDiffUtil(folderList, newList))
        folderList = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FolderAdapterDiffUtil(
        private val oldList: List<Folder>,
        private val newList: List<Folder>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].folderName == newList[newItemPosition].folderName)
        }
    }

}


class GridSpacingItemDecoration(
    private val spanCount: Int,    // Number of columns
    private val spacing: Int,      // Spacing in pixels
    private val includeEdge: Boolean // Whether to include edge spacing
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // Item position
        val column = position % spanCount                  // Column index

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // Top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // Item bottom
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing // Item top
            }
        }
    }
}
