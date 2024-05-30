package pradio.ep.githubuser.domain.model

import pradio.ep.githubuser.data.remote.response.UserFollowingItemResponse

data class UserFollowing(
    val avatarUrl: String?,
    val id: Int?,
    val login: String?,
) {
    companion object {
        fun parse(data: List<UserFollowingItemResponse>): List<UserFollowing> {
            return data.map {
                UserFollowing(
                    avatarUrl = it.avatarUrl,
                    id = it.id,
                    login = it.login
                )
            }
        }
    }
}