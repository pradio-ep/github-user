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

    suspend fun getUserFollowers(username: String) : Flow<ResultState<List<UserFollower>>>

    suspend fun getUserFollowing(username: String) : Flow<ResultState<List<UserFollowing>>>

    /**
     * Local
     */
    fun fetchAllUserFavorite() : Flow<List<UserFavorite>>

    fun getFavoriteUserByUsername(username: String) : Flow<List<UserFavorite>>

    suspend fun addUserToFavDB(userFavorite: UserFavorite)

    suspend fun deleteUserFromFavDB(userFavorite: UserFavorite)
}