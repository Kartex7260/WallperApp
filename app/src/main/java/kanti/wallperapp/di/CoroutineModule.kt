package kanti.wallperapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ServiceComponent::class)
class CoroutineModule {

	@DispatcherIO
	@Provides
	fun provideCoroutineContext(): CoroutineContext {
		return Dispatchers.Default
	}

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIO