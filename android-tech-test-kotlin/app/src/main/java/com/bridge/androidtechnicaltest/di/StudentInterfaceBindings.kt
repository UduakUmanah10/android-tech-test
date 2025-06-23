package com.bridge.androidtechnicaltest.di
import com.bridge.androidtechnicaltest.data.repository.PupilRepositoryImpl2
import com.bridge.androidtechnicaltest.data.repository.StudentRepoImpl
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindStudentRepository(
        impl: StudentRepoImpl
    ): PupilRepository

    @Binds
    abstract fun bindPupilRepository(
        impl: PupilRepositoryImpl2
    ): PupilRepository1
}
