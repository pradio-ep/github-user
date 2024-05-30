package pradio.ep.githubuser.domain.repository

import kotlinx.coroutines.flow.Flow
import pradio.ep.githubuser.domain.model.*
import pradio.ep.githubuser.util.state.ResultState

interface UserRepository {

    /**
     * Remote
     */
    suspend fun getUser(username: String) : Flow<ResultState<List<UserSearch>>>

    suspend fun getDetailUser(username: String) : Flow<ResultState<UserDetail>>

    suspend fun getUserRepo(username: String) : Flow<ResultState<List<UserRepo>>>

}