package pradio.ep.githubuser.domain.usecase

import pradio.ep.githubuser.domain.model.UserFavorite
import pradio.ep.githubuser.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UserUseCase {
    /**
     * Remote
     */
    override suspend fun getUser(username: String) =
        userRepository.getUser(username)

    override suspend fun getUserDetail(username: String) =
        userRepository.getDetailUser(username)

    override suspend fun getUserRepo(username: String) =
        userRepository.getUserRepo(username)

    override suspend fun getUserFollowers(username: String) =
        userRepository.getUserFollowers(username)

    override suspend fun getUserFollowing(username: String) =
        userRepository.getUserFollowing(username)

    /**
     * Local
     */
    override fun fetchAllUserFavorite() =
        userRepository.fetchAllUserFavorite()

    override fun getFavUserByUsername(username: String) =
        userRepository.getFavoriteUserByUsername(username)

    override suspend fun addUserToFavDB(userFavorite: UserFavorite) {
        try {
            userRepository.addUserToFavDB(userFavorite)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    override suspend fun deleteUserFromDb(userFavorite: UserFavorite) {
        try {
            userRepository.deleteUserFromFavDB(userFavorite)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}