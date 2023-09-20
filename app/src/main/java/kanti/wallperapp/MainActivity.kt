package kanti.wallperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.data.repositories.RepositoryResultType
import kanti.wallperapp.databinding.ActivityMainBinding
import kanti.wallperapp.view.TagListRecyclerAdapter
import kanti.wallperapp.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var view: ActivityMainBinding
	private val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityMainBinding.inflate(layoutInflater)
		setContentView(view.root)

		title = getString(R.string.activity_main_title)

		viewModel.tagsLiveData.observe(this) { tagsUiState ->
			view.recyclerViewImages.removeAllViews()
			when (tagsUiState.tags.resultType) {
				RepositoryResultType.Success -> {
					val tagListRecyclerAdapter = TagListRecyclerAdapter(tagsUiState.tags.data!!, ::onClickTagItem)
					view.recyclerViewImages.adapter = tagListRecyclerAdapter
				}
				RepositoryResultType.NotConnection -> {
					Toast.makeText(this, R.string.activity_main_error_connection, Toast.LENGTH_SHORT).show()
					lifecycleScope.launch {
						delay(2000L)
						viewModel.getTags()
					}
				}
				RepositoryResultType.Fail -> {
					Toast.makeText(this, R.string.activity_main_unexpected_error, Toast.LENGTH_SHORT).show()
				}
			}
		}
		viewModel.getTags()
	}

	private fun onClickTagItem(tagName: String) {
	}

}