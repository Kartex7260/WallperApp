package kanti.wallperapp.data.repositories

data class RepositoryResult<T>(
	val data: T? = null,
	val resultType: RepositoryResultType = RepositoryResultType.Success
)

enum class RepositoryResultType {
	Success,
	NotConnection,
	Fail
}