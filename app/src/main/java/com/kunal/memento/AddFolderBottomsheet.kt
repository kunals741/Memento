package com.kunal.memento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kunal.memento.databinding.BottomsheetAddFolderBinding

class AddFolderBottomsheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetAddFolderBinding

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetAddFolderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            viewModel.addNewFolder(binding.etSearch.text.trim().toString())
            dismiss()
        }
    }

    companion object {
        const val TAG = "AddFolderBottomsheet"
        fun showAddFolderBottomsheet(
            fragmentManager: FragmentManager
        ) = AddFolderBottomsheet().show(fragmentManager, TAG)
    }
}