package pradio.ep.githubuser.data.remote

import pradio.ep.githubuser.BuildConfig
import pradio.ep.githubuser.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    /**
     * Endpoints search User
     */
    @GET("search/users?")
    @Headers("Autohorization: token $apiKey")
    suspend fun getSearchUser(
        @Query("q") q : String
    ) : UserSearchResponse

    /**
     * Endpoints Detail User
     */
    @GET("users/{username}")
    @Headers("Autohorization: token $apiKey")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : UserDetailResponse

    /**
     * Endpoints Repo
     */
    @GET("users/{username}/repos")
    @Headers("Autohorization: token $apiKey")
    suspend fun getRepoUser(
        @Path("username") username: String
    ) : UserRepoResponse

    companion object {
        private const val apiKey: String = BuildConfig.API_KEY
    }
}