package pradio.ep.githubuser.domain.model

import pradio.ep.githubuser.data.remote.response.UserFollowersItemResponse

data class UserFollower(
    val avatarUrl: String?,
    val id: Int?,
    val login: String?,
) {
    companion object {
        fun parse(data: List<UserFollowersItemResponse>): List<UserFollower> {
            return data.map {
                UserFollower(
                    avatarUrl = it.avatarUrl,
                    id = it.id,
                    login = it.login
                )
            }
        }
    }
}