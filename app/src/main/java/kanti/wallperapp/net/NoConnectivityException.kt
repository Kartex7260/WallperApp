package kanti.wallperapp.net

import java.io.IOException

class NoConnectivityException(message: String, cause: Throwable?) : IOException(message, cause) {

	constructor(message: String) : this(message, null)

	constructor(cause: Throwable?) : this("", cause)

	constructor() : this("", null)

}