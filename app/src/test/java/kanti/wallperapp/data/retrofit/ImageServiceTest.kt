package kanti.wallperapp.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageServiceTest {

	private val url = "https://api.imgur.com/3/"
	private val clientId = "7f001619d3640ad"
	private val retrofit: Retrofit
	private val imageService: ImageService

	init {
		val interceptor = Interceptor { chain ->
			val newRequest = chain.request().newBuilder()
				.addHeader("Authorization", "Client-ID ${clientId}")
				.build()
			chain.proceed(newRequest)
		}

		val client = OkHttpClient.Builder()
			.addInterceptor(interceptor)
			.build()

		retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.baseUrl(url)
			.build()

		imageService = retrofit.create(ImageService::class.java)
	}

	@Test
	fun getAllTagsTest() {
		val allTagsCall = imageService.getAllTags()
		val allTagsResponse = allTagsCall.execute()

		val isSuccess = allTagsResponse.body()?.success ?: false
		Assert.assertTrue("Success", isSuccess)
		val isNotNull = allTagsResponse.body()?.data != null
		Assert.assertTrue("Data null", isNotNull)
	}

	@Test
	fun getTagImagesIDTest() {
		val allTagsCall = imageService.getAllTags()
		val allTagsResponse = allTagsCall.execute()
		val tagName: String = allTagsResponse.body()?.data?.tags?.get(0)?.name ?: return

		val tagImagesIdCall = imageService.getTagImagesId(tagName)
		val tagImagesIdResponse = tagImagesIdCall.execute()

		val isSuccess = tagImagesIdResponse.body()?.success ?: false
		Assert.assertTrue("Success", isSuccess)
		val isNotNull = tagImagesIdResponse.body()?.data != null
		Assert.assertTrue("Data is null", isNotNull)
	}

}