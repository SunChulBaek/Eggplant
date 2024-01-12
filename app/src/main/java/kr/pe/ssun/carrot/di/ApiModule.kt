package kr.pe.ssun.carrot.di

import kr.pe.ssun.carrot.network.ktor.KtorBookNetwork
import kr.pe.ssun.carrot.data.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesFakeRepository(
        apiService: KtorBookNetwork
    ): BookRepository {
        return BookRepository(apiService)
    }
}