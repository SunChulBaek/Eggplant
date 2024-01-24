package kr.pe.ssun.eggplant.di

import kr.pe.ssun.eggplant.data.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.pe.ssun.eggplant.network.EggplantBookNetwork
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesBookRepository(
        apiService: EggplantBookNetwork
    ): BookRepository {
        return BookRepository(apiService)
    }
}