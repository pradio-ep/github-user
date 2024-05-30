package pradio.ep.githubuser.domain.model

import pradio.ep.githubuser.data.remote.response.UserSearchItemResponse

data class UserSearch(
    val avatarUrl: String?,
    val id: Int?,
    val login: String?,
) {
    companion object {
        fun parse(data: List<UserSearchItemResponse>): List<UserSearch> {
            return data.map {
                UserSearch(
                    avatarUrl = it.avatarUrl,
                    id = it.id,
                    login = it.login,
                )
            }
        }
    }
}