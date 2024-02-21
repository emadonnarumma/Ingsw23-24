plugins {
    id("com.android.application")
}

android {
    namespace = "com.ingsw.dietiDeals24"
    compileSdk = 34

    defaultConfig {

        applicationId = "com.ingsw.dietiDeals24"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    dataBinding {
        enable = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("jp.hamcheesedev:outlinedtextview:0.1.0")

    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-core:21.1.1")

    implementation("com.wx.wheelview:wheelview:1.3.3")

    implementation("com.wdullaer:materialdatetimepicker:4.2.3")

    implementation("com.jsibbold:zoomage:1.3.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.github.smarteist:autoimageslider:1.4.0")

    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")

    implementation("com.github.chivorns:smartmaterialspinner:1.5.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.2")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val navigatorVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment:$navigatorVersion")
    implementation("androidx.navigation:navigation-ui:$navigatorVersion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navigatorVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigatorVersion")
    implementation("androidx.navigation:navigation-compose:$navigatorVersion")

    implementation("com.google.android.material:material:1.11.0")

    implementation("com.squareup.retrofit2:retrofit:2.7.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.stepstone.stepper:material-stepper:4.3.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


