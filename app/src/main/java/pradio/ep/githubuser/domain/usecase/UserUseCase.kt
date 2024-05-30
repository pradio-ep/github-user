package pradio.ep.githubuser.domain.usecase

import kotlinx.coroutines.flow.Flow
import pradio.ep.githubuser.domain.model.*
import pradio.ep.githubuser.util.state.ResultState

interface UserUseCase {
    suspend fun getUser(username : String) : Flow<ResultState<List<UserSearch>>>
    suspend fun getUserDetail(username : String) : Flow<ResultState<UserDetail>>
    suspend fun getUserRepo(username : String) :  Flow<ResultState<List<UserRepo>>>
}