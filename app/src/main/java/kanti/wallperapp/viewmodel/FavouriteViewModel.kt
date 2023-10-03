package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import kanti.wallperapp.viewmodel.uistate.FavouriteUiState

interface FavouriteViewModel<T> {

	fun onFavourite(value: T, isFavourite: Boolean): LiveData<T>

	fun syncFavouriteData(values: List<T>): LiveData<FavouriteUiState<T>>

}