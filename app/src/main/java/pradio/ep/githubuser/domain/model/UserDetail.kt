package pradio.ep.githubuser.domain.model

import pradio.ep.githubuser.data.remote.response.UserDetailResponse

data class UserDetail (
    val username: String,
    val name: String?,
    val avatarUrl: String?,
    val followingUrl: String?,
    val bio: String?,
    val company: String?,
    val publicRepos: Int?,
    val followersUrl: String?,
    val followers: Int?,
    val following: Int?,
    val location: String?
) {
    companion object {
        fun parse(data: UserDetailResponse): UserDetail {
            return UserDetail(
                username = data.login.toString(),
                name = data.name,
                avatarUrl = data.avatarUrl,
                followersUrl = data.followersUrl,
                bio = data.bio,
                company = data.company,
                publicRepos = data.publicRepos,
                followingUrl = data.followingUrl,
                followers = data.followers,
                following = data.following,
                location = data.location
            )
        }
    }
}