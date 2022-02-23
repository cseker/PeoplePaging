package com.cihadseker.main.di

import com.cihadseker.main.data.repository.DataSource
import com.cihadseker.main.data.repository.PeopleRepository
import com.cihadseker.main.data.repository.PeopleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDataSource() = DataSource()

    @Singleton
    @Provides
    fun providePeopleRepository(dataSource: DataSource): PeopleRepository = PeopleRepositoryImpl(dataSource)
}
