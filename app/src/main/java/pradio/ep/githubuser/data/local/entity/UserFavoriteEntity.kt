package pradio.ep.githubuser.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pradio.ep.githubuser.domain.model.UserFavorite

@Entity(tableName = "user_favorite_table")
data class UserFavoriteEntity(
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "username") val username: String,
	@ColumnInfo(name = "name") val name: String?,
	@ColumnInfo(name = "avatar_url") val avatarUrl: String?,
	@ColumnInfo(name = "bio") val bio: String?,
	@ColumnInfo(name = "company") val company: String?,
	@ColumnInfo(name = "location") val location: String?
) {
	companion object {
		fun parse(data: UserFavorite): UserFavoriteEntity {
			return UserFavoriteEntity(
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
