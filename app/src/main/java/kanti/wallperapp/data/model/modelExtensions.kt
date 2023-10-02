package kanti.wallperapp.data.model

import kanti.wallperapp.data.room.RoomImageData
import kanti.wallperapp.data.room.RoomTag

fun Tag.asRoomTag(): RoomTag {
	return RoomTag(name, displayName, position)
}

fun RoomTag.asTag(): Tag {
	return Tag(name, displayName, true, position)
}

fun ImageData.asRoomImageData(): RoomImageData {
	return RoomImageData(link, title, position)
}

fun RoomImageData.asImageData(): ImageData {
	return ImageData(title, link, true, position)
}