package com.kunal.memento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kunal.memento.ViewExtensions.getParcelableCompat
import com.kunal.memento.constants.IntentKeyConstants
import com.kunal.memento.databinding.BottomsheetGetUserInputBinding
import com.kunal.memento.model.BottomsheetRequestData

class GetUserInputBottomsheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetGetUserInputBinding

    private var onActionBtnClick: ((String) -> Unit)? = null

    private val bottomsheetRequestData: BottomsheetRequestData by lazy {
        arguments?.getParcelableCompat(IntentKeyConstants.BOTTOMSHEET_REQUEST_DATA)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetGetUserInputBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvTitle.text = bottomsheetRequestData.title
            etInput.hint = bottomsheetRequestData.hintText
            btnAdd.text = bottomsheetRequestData.buttonText
        }

        binding.btnAdd.setOnClickListener {
            onActionBtnClick?.invoke(binding.etInput.text.trim().toString())
            dismiss()
        }
    }

    companion object {
        private const val TAG = "GetUserInputBottomsheet"
        fun showGetUserInputBottomsheet(
            bottomsheetRequestData: BottomsheetRequestData,
            fragmentManager: FragmentManager,
            onActionBtnClick: ((String) -> Unit)? = null
        ) = GetUserInputBottomsheet().apply {
            this.onActionBtnClick = onActionBtnClick
            arguments = Bundle().apply {
                putParcelable(IntentKeyConstants.BOTTOMSHEET_REQUEST_DATA, bottomsheetRequestData)
            }
        }.show(fragmentManager, TAG)
    }
}