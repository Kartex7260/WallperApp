package kanti.wallperapp.data

import kanti.wallperapp.data.retrofit.DataResponse

interface TagsRemoteDataSource {

	suspend fun getTags(): DataResponse<List<Tag>>

}