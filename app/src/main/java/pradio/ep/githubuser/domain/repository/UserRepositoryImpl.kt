package pradio.ep.githubuser.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pradio.ep.githubuser.data.remote.NetworkService
import pradio.ep.githubuser.domain.model.*
import pradio.ep.githubuser.util.state.ResultState
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : UserRepository {

    /**
     * Remote
     */
    override suspend fun getUser(username: String): Flow<ResultState<List<UserSearch>>> {
        return flow {
            try {
                val response = networkService.getSearchUser(username)
                val userItems = response.userItems
                val parseData = userItems?.let { listSearchUser ->
                    UserSearch.parse(listSearchUser)
                }
                emit(ResultState.Success(parseData))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailUser(username: String): Flow<ResultState<UserDetail>> {
        return flow {
            try {
                val response = networkService.getDetailUser(username)
                val parseData = UserDetail.parse(response)
                emit(ResultState.Success(parseData))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserRepo(username: String): Flow<ResultState<List<UserRepo>>> {
        return flow {
            try {
                val response = networkService.getRepoUser(username)
                val parseData = UserRepo.parse(response)
                emit(ResultState.Success(parseData))
            } catch (e: java.lang.Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }
}