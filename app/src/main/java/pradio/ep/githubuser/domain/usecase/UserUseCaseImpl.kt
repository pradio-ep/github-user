package pradio.ep.githubuser.domain.usecase

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
}