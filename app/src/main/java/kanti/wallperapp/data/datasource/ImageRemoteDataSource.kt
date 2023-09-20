package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.repositories.Image

interface ImageRemoteDataSource {

	suspend fun getImages(tagName: String): RemoteDataResult<List<Image>>

}