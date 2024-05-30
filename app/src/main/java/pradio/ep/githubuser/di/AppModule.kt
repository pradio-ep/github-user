package pradio.ep.githubuser.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

}