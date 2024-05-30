package pradio.ep.githubuser.data.remote.response


import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("items")
    val userItems: List<UserSearchItemResponse>?,
    @SerializedName("total_count")
    val totalCount: Int?
)