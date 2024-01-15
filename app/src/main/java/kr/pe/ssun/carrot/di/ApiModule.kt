package kr.pe.ssun.carrot.di

import kr.pe.ssun.carrot.data.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.pe.ssun.carrot.network.CarrotBookNetwork
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesBookRepository(
        apiService: CarrotBookNetwork
    ): BookRepository {
        return BookRepository(apiService)
    }
}