package kanti.wallperapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.RepositoryResultType
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
			if (tagsUiState.process || tagsUiState.tags == null) {
				view.recyclerViewImages.adapter = null
				view.progressBarMain.visibility = View.VISIBLE
				return@observe
			}

			view.progressBarMain.visibility = View.INVISIBLE
			view.recyclerViewImages.adapter = TagsRecyclerAdapter(
				tagsUiState.tags.data ?: listOf(),
				::onClickTagItem,
				viewModel
			)

			when (tagsUiState.tags.resultType) {
				RepositoryResultType.NotConnection -> {
					Toast.makeText(this, R.string.net_error_connection, Toast.LENGTH_SHORT).show()
				}
				RepositoryResultType.Fail -> {
					Toast.makeText(this, R.string.net_unexpected_error, Toast.LENGTH_SHORT).show()
				}
				else -> {}
			}
		}
		viewModel.getTags()
	}

	override fun onResume() {
		super.onResume()
		notifyTagCardView()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_main_option, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
		R.id.menu_main_option_refresh -> {
			viewModel.getTags()
			true
		}
		R.id.menu_main_option_settings -> {
			SettingsActivity.startActivity(this)
			true
		}
		R.id.menu_main_option_favourites -> {
			FavouritesActivity.startActivity(this)
			true
		}
		else -> {
			super.onOptionsItemSelected(item)
		}
	}

	private fun onClickTagItem(tag: Tag) {
		ImagesActivity.startActivity(this, tag)
	}

	private fun notifyTagCardView() {
		val adapter = view.recyclerViewImages.adapter as TagsRecyclerAdapter? ?: return
		val updateLiveData = viewModel.updateFavouriteTags(adapter.tags)
		updateLiveData.observe(this) { index ->
			adapter.notifyItemChanged(index)
		}
	}

}