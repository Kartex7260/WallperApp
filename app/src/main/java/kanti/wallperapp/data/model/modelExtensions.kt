package kanti.wallperapp.data.model

import kanti.wallperapp.data.room.FavoriteTag

fun Tag.toFavoriteTag(): FavoriteTag {
	return FavoriteTag(name, displayName)
}

fun FavoriteTag.toTag(): Tag {
	return Tag(name, displayName)
}