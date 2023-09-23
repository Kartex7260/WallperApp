package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.ImageLink

interface ImageLinksRemoteDataSource {

	suspend fun getImages(tagName: String): RemoteDataResult<List<ImageLink>>

}