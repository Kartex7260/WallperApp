package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.ImageData

interface ImageLinksRemoteDataSource {

	suspend fun getImages(tagName: String): RemoteDataResult<List<ImageData>>

}