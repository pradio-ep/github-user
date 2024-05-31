package pradio.ep.githubuser.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pradio.ep.githubuser.BuildConfig
import pradio.ep.githubuser.data.local.dao.UserFavoriteDao
import pradio.ep.githubuser.data.local.db.AppDatabase
import pradio.ep.githubuser.data.remote.Network
import pradio.ep.githubuser.data.remote.NetworkService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkService(@ApplicationContext context: Context): NetworkService {
        return Network.retrofitClient(context).create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            BuildConfig.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserFavoriteDao(appDatabase: AppDatabase): UserFavoriteDao {
        return appDatabase.userFavDao()
    }

}