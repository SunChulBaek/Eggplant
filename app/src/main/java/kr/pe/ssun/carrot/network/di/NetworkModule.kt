package kr.pe.ssun.carrot.network.di

import kr.pe.ssun.carrot.network.BookNetworkDataSource
import kr.pe.ssun.carrot.network.ktor.KtorBookNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindKtorMarvelNetwork(
        network: KtorBookNetwork
    ): BookNetworkDataSource
}