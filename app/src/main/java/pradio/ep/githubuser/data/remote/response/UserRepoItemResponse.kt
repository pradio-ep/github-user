package pradio.ep.githubuser.data.remote.response


import com.google.gson.annotations.SerializedName

data class UserRepoItemResponse(
    @SerializedName("private")
    val jsonMemberPrivate: Boolean,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("node_id")
    val nodeId: String
)