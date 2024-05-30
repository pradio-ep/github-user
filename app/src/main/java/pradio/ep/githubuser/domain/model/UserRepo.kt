package pradio.ep.githubuser.domain.model

import pradio.ep.githubuser.data.remote.response.UserRepoItemResponse

data class UserRepo (
    val id: Int?,
    val name: String?,
    val description: String?
) {
    companion object {
        fun parse(data: List<UserRepoItemResponse>): List<UserRepo> {
            return data.map {
                UserRepo(
                    id = it.id,
                    name = it.name,
                    description = it.description
                )
            }
        }
    }
}