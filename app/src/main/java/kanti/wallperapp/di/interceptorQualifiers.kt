package kanti.wallperapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthorizationHeadInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoConnectionInterceptor