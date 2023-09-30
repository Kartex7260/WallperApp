package kanti.wallperapp.data.model

import kanti.wallperapp.data.room.FavouriteTag

fun Tag.toFavoriteTag(): FavouriteTag {
	return FavouriteTag(name, displayName, position)
}

fun FavouriteTag.toTag(): Tag {
	return Tag(name, displayName, true, position)
}