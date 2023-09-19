import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
	id("com.google.dagger.hilt.android")
}

tasks.withType<KaptGenerateStubsTask>().configureEach {
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

android {
	namespace = "kanti.wallperapp"
	compileSdk = 33

	defaultConfig {
		applicationId = "kanti.wallperapp"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.9.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
	implementation("androidx.fragment:fragment-ktx:1.6.1")
	implementation("androidx.recyclerview:recyclerview:1.3.1")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
	implementation("androidx.preference:preference-ktx:1.2.1")

	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("io.coil-kt:coil:2.4.0")

	implementation("com.google.dagger:hilt-android:2.48")
	kapt("com.google.dagger:hilt-android-compiler:2.48")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}