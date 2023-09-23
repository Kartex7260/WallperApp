package kanti.wallperapp.net

import android.util.Log
import kanti.wallperapp.data.retrofit.ConnectionService
import kotlinx.coroutines.CancellationException
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject

class ConnectionChecker @Inject constructor(
	private val connectionService: ConnectionService
) {

	private val logTag = "ConnectionChecker"

	suspend fun isConnection(): Boolean {
		connectionService.apply {
			val google = checkGoogle().calling()
			if (google)
				return true

			val yandex = checkYandex().calling()
			if (yandex)
				return true

			val yahoo = checkYahoo().calling()
			if (yahoo)
				return true

			val bing = checkBing().calling()
			if (bing)
				return true
		}
		return false
	}

	private suspend fun Call<Unit>.calling(): Boolean {
		val response: Response<Unit>?
		try {
			response = awaitResponse()
		} catch (ex: Exception) {
			Log.i(logTag, ex.message, ex)
			return false
		} catch (ex: CancellationException) {
			throw ex
		}
		if (response.isSuccessful) {
			Log.i(logTag, "success connection")
			return true
		}
		return false
	}

}