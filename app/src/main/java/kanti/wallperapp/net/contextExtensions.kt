package kanti.wallperapp.net

import android.content.Context
import android.net.ConnectivityManager

val Context.isConnection: Boolean
	get() {
		val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		connectivityManager.activeNetworkInfo?.also {
			it.isConnected
		}
		return true
		TODO("Сделать проверку сети рабочей")
	}

