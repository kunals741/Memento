package com.kunal.memento

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kunal.memento.adapter.FolderAdapter
import com.kunal.memento.adapter.GridSpacingItemDecoration
import com.kunal.memento.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        rvFolders.adapter = FolderAdapter()
        rvFolders.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                Utils.dpToPx(this@MainActivity, 12),
                false
            )
        )
        rvFolders.itemAnimator = null //removing item adding animation
        fabActionButton.setOnClickListener {
            AddFolderBottomsheet.showAddFolderBottomsheet(supportFragmentManager)
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

    private fun getFolderAdapter() = (binding.rvFolders.adapter as FolderAdapter)
}