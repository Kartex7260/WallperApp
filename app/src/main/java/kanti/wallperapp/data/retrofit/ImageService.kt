package kanti.wallperapp.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageService {

	@GET("tags")
	fun getAllTags(): Call<MetaData<TagsDTO>>

	@GET("gallery/t/{tagName}")
	fun getTagImagesId(@Path("tagName") tagName: String): Call<MetaData<TagItemsDTO>>

}