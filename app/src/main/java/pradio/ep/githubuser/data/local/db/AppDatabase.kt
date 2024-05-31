package pradio.ep.githubuser.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pradio.ep.githubuser.data.local.dao.UserFavoriteDao
import pradio.ep.githubuser.data.local.entity.UserFavoriteEntity

@Database(
    entities = [UserFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userFavDao(): UserFavoriteDao
}