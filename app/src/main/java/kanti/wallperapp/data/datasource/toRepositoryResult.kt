package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.repositories.RepositoryResult
import kanti.wallperapp.data.repositories.RepositoryResultType

fun <Data> RemoteDataResult<Data>.toRepositoryResult(): RepositoryResult<Data> {
	val resultType = when (resultType) {
		RemoteDataResultType.Success -> RepositoryResultType.Success
		RemoteDataResultType.NotConnection -> RepositoryResultType.NotConnection
		else -> RepositoryResultType.Fail
	}
	return RepositoryResult(data, resultType)
}
