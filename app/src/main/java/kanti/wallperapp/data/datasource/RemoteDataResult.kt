package kanti.wallperapp.data.datasource

data class RemoteDataResult<T>(
	val data: T? = null,
	val resultType: RemoteDataResultType = RemoteDataResultType.Success
)

enum class RemoteDataResultType {
	Success,
	Unauthorized,
	NotFound,
	NotConnection,
	Fail
}