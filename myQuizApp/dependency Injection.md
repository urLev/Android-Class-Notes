# Adding dependencies to projects

In `libs.versions.toml`

```toml
[versions]

room = "2.6.1"
ksp = "2.0.21-1.0.26"

[libraries]
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room"}
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room"}
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room"}

[plugins]
google-devtools-ksp = {id = "com.google.devtools.ksp", version.ref = "ksp"}
```
In `build.gradle.kts (MyAppName)`

```kotlin
alias(libs.plugins.google.devtools.ksp) apply false
```

In `build.gradle.kts (:app)`

```kotlin
plugins {
    alias(lilbs.plugins.google.devtools.ksp)
}

dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
```

