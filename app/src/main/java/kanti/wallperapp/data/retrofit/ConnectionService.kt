package kanti.wallperapp.data.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ConnectionService {

	@GET("https://www.google.com/")
	fun checkGoogle(): Call<Unit>

	@GET("https://ya.ru/")
	fun checkYandex(): Call<Unit>

	@GET("https://www.yahoo.com/")
	fun checkYahoo(): Call<Unit>

	@GET("https://www.bing.com/")
	fun checkBing(): Call<Unit>

}