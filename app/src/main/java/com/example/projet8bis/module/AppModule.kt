package com.example.projet8bis.module

import android.content.Context
import com.example.projet8bis.data.DataRepository
import com.example.projet8bis.database.AppDataBase
import com.example.projet8bis.retrofit.ApiService
import com.example.projet8bis.retrofit.CallApi
import com.example.projet8bis.room.dao.ListDao



import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context, coroutineScope: CoroutineScope): AppDataBase {
        return AppDataBase.getDatabase(context, coroutineScope)
    }

    @Provides
    fun provideUserDao(database: AppDataBase): ListDao {
        return database.listDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(listDao: ListDao): DataRepository {
        return DataRepository(listDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.api)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCallApi(): CallApi {
        return CallApi
    }
}
