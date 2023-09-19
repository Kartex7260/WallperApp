package kanti.wallperapp.data

import kanti.wallperapp.data.retrofit.DataResponse

interface ImageRemoteDataSource {

	suspend fun getImages(tagName: String): DataResponse<List<Image>>

}