package kanti.wallperapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.R
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.net.NoConnectivityException
import kanti.wallperapp.net.isConnection
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@AuthorizationHeadInterceptor
	@Provides
	fun provideAuthorizationInterceptor(@ApplicationContext context: Context): Interceptor {
		val clientId = context.getString(R.string.imgur_client_id)
		return Interceptor { chain ->
			val newRequest = chain.request().newBuilder()
				.addHeader("Authorization", "Client-ID $clientId")
				.build()
			chain.proceed(newRequest)
		}
	}

	@NoConnectionInterceptor
	@Provides
	fun provideNoConnectionInterceptor(@ApplicationContext context: Context): Interceptor {
		return Interceptor { chain ->
			if (context.isConnection) {
				chain.proceed(chain.request())
			} else {
				throw NoConnectivityException()
			}
		}
	}

	@Provides
	@Singleton
	fun provideRetrofit(
		@ApplicationContext context: Context,
		@AuthorizationHeadInterceptor authInterceptor: Interceptor,
		@NoConnectionInterceptor noConnectionInterceptor: Interceptor
	): Retrofit {
		val client = OkHttpClient.Builder()
			.addInterceptor(authInterceptor)
			.addInterceptor(noConnectionInterceptor)
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