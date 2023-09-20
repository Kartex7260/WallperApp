package kanti.wallperapp.data.retrofit

import kanti.wallperapp.data.datasource.RemoteDataResult
import kanti.wallperapp.data.datasource.RemoteDataResultType
import retrofit2.Response

fun <DTO, Data> Response<DTO>.toDataSourceResult(
	converter: (DTO?) -> Data?
): RemoteDataResult<Data> {
	val resultType = when (code()) {
		200 -> RemoteDataResultType.Success
		401 -> RemoteDataResultType.Unauthorized
		404 -> RemoteDataResultType.NotFound
		503 -> RemoteDataResultType.NotConnection
		else -> RemoteDataResultType.Fail
	}
	val data = converter(body())
	return RemoteDataResult(data, resultType)
}