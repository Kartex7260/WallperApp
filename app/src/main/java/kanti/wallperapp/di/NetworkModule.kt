package kanti.wallperapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.R
import kanti.wallperapp.data.retrofit.ImageService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Headers("Authorization: Client-ID ")
	@Provides
	@Singleton
	fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
		val clientId = context.getString(R.string.imgur_client_id)
		val interceptor = Interceptor { chain ->
			val newRequest = chain.request().newBuilder()
				.addHeader("Authorization", "Client-ID $clientId")
				.build()
			chain.proceed(newRequest)
		}

		val client = OkHttpClient.Builder()
			.addInterceptor(interceptor)
			.build()

		return Retrofit.Builder()
			.client(client)
			.baseUrl(context.getString(R.string.imgur_link))
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Provides
	@Singleton
	fun provideImageService(retrofit: Retrofit): ImageService =
		retrofit.create(ImageService::class.java)

}