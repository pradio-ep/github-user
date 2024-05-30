package pradio.ep.githubuser.data.remote.response


import com.google.gson.annotations.SerializedName

data class UserSearchItemResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?,
)