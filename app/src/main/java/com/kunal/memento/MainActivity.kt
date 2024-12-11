package com.kunal.memento

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kunal.memento.adapter.FolderAdapter
import com.kunal.memento.adapter.FolderClickListener
import com.kunal.memento.adapter.GridSpacingItemDecoration
import com.kunal.memento.constants.SharedPrefConstants
import com.kunal.memento.databinding.ActivityMainBinding
import com.kunal.memento.db.entity.Folders
import com.kunal.memento.model.BottomsheetRequestData
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), FolderClickListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(
            SharedPrefConstants.APP_PREF,
            MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObservers()
        viewModel.getAllFolders()
    }

    private fun initView() = with(binding) {
        handleUserName()
        rvFolders.adapter = FolderAdapter(folderClickListener = this@MainActivity)
        rvFolders.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                Utils.dpToPx(this@MainActivity, 12),
                false
            )
        )
        rvFolders.itemAnimator = null //removing item adding animation
        fabActionButton.setOnClickListener {
            val bottomsheetRequestData = BottomsheetRequestData(
                getString(R.string.create_new_folder),
                resources.getString(R.string.create),
                resources.getString(R.string.folder_name)
            )

            GetUserInputBottomsheet.showGetUserInputBottomsheet(
                bottomsheetRequestData,
                supportFragmentManager
            ) { folderName ->
                viewModel.addNewFolder(folderName)
            }
        }
    }

    private fun initObservers() = viewModel.apply {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                folderList.collect {
                    getFolderAdapter().updateData(it)
                }
            }
        }
    }

    private fun handleUserName() {
        val userName: String? = sharedPref.getString(SharedPrefConstants.USER_NAME, "")
        if (userName.isNullOrEmpty()) {
            val bottomsheetRequestData = BottomsheetRequestData(
                title = getString(R.string.add_user_name),
                buttonText = getString(R.string.add),
                hintText = getString(R.string.enter_user_name)
            )
            GetUserInputBottomsheet.showGetUserInputBottomsheet(
                bottomsheetRequestData,
                supportFragmentManager
            ) { enteredName ->
                sharedPref.edit().putString(
                    SharedPrefConstants.USER_NAME,
                    enteredName
                ).apply()
                binding.tvHeader.text = getString(R.string.hello_user, enteredName)
            }
        } else {
            binding.tvHeader.text = getString(R.string.hello_user, userName)
        }
    }

    private fun getFolderAdapter() = (binding.rvFolders.adapter as FolderAdapter)

    override fun onFolderClick(folder: Folders) {
        NoteListActivity.startNoteListActivity(folder, this)
    }
}