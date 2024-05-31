package pradio.ep.githubuser.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import pradio.ep.githubuser.data.local.dao.UserFavoriteDao
import pradio.ep.githubuser.data.local.entity.UserFavoriteEntity
import pradio.ep.githubuser.data.remote.NetworkService
import pradio.ep.githubuser.domain.model.*
import pradio.ep.githubuser.util.state.ResultState
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val userFavoriteDao: UserFavoriteDao
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

    override suspend fun getUserFollowers(username: String): Flow<ResultState<List<UserFollower>>> {
        return flow {
            try {
                val response = networkService.getFollowerUser(username)
                val parseData = UserFollower.parse(response)
                emit(ResultState.Success(parseData))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserFollowing(username: String): Flow<ResultState<List<UserFollowing>>> {
        return flow {
            try {
                val response = networkService.getFollowingUser(username)
                val parseData = UserFollowing.parse(response)
                emit(ResultState.Success(parseData))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString(), 500))
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Local
     */
    override fun fetchAllUserFavorite(): Flow<List<UserFavorite>> {
        return userFavoriteDao.fetchAllUsers().map {
            UserFavorite.parse(it)
        }
    }

    override fun getFavoriteUserByUsername(username: String): Flow<List<UserFavorite>> {
        return userFavoriteDao.getFavByUsername(username).map {
            UserFavorite.parse(it)
        }
    }

    override suspend fun addUserToFavDB(userFavorite: UserFavorite) {
        val data = UserFavoriteEntity.parse(userFavorite)
        userFavoriteDao.addUserToFavoriteDB(data)
    }

    override suspend fun deleteUserFromFavDB(userFavorite: UserFavorite) {
        val data = UserFavoriteEntity.parse(userFavorite)
        userFavoriteDao.deleteUserFromFavoriteDB(data)
    }
}