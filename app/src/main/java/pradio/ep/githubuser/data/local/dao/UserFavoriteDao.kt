package pradio.ep.githubuser.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pradio.ep.githubuser.data.local.entity.UserFavoriteEntity

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite_table")
    fun fetchAllUsers() : Flow<List<UserFavoriteEntity>>

    @Query("SELECT * FROM user_favorite_table WHERE username = :userName")
    fun getFavByUsername(userName: String) : Flow<List<UserFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserToFavoriteDB(userEntity: UserFavoriteEntity)

    @Delete
    suspend fun deleteUserFromFavoriteDB(userEntity: UserFavoriteEntity)

}