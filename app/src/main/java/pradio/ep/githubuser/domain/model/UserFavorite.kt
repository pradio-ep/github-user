package pradio.ep.githubuser.domain.model

import pradio.ep.githubuser.data.local.entity.UserFavoriteEntity


data class UserFavorite(
    val username: String,
    val name: String?,
    val avatarUrl: String?,
    val bio: String?,
    val company: String?,
    val location: String?
) {
    companion object {
        fun parse(data: List<UserFavoriteEntity>): List<UserFavorite> {
            return data.map {
                UserFavorite(
                    username = it.username,
                    name = it.name,
                    avatarUrl = it.avatarUrl,
                    bio = it.bio,
                    company = it.company,
                    location = it.location
                )
            }
        }
        fun parse(data: UserDetail): UserFavorite {
            return UserFavorite(
                username = data.username,
                name = data.name,
                avatarUrl = data.avatarUrl,
                bio = data.bio,
                company = data.company,
                location = data.location
            )
        }
    }
}