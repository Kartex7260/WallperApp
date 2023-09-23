package kanti.wallperapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.data.repositories.RepositoryResultType
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.databinding.ActivityMainBinding
import kanti.wallperapp.view.TagsRecyclerAdapter
import kanti.wallperapp.viewmodel.MainViewModel

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
					val tagListRecyclerAdapter = TagsRecyclerAdapter(tagsUiState.tags.data ?: listOf(), ::onClickTagItem)
					view.recyclerViewImages.adapter = tagListRecyclerAdapter
				}
				RepositoryResultType.NotConnection -> {
					Toast.makeText(this, R.string.net_error_connection, Toast.LENGTH_SHORT).show()
				}
				RepositoryResultType.Fail -> {
					Toast.makeText(this, R.string.net_unexpected_error, Toast.LENGTH_SHORT).show()
				}
			}
		}
		viewModel.getTags()
	}

	private fun onClickTagItem(tag: Tag) {
		ImagesActivity.startActivity(this, tag)
	}

}