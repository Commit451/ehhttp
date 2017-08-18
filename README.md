# ehHttp
OkHttp, eh? Converts OkHttp `Call`s to RxJava types

[![Build Status](https://travis-ci.org/Commit451/ehhttp.svg?branch=master)](https://travis-ci.org/Commit451/ehhttp) [![](https://jitpack.io/v/Commit451/ehhttp.svg)](https://jitpack.io/#Commit451/ehhttp)

## Gradle Dependency
Add the jitpack url to the project:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
then, in your app `build.gradle`
```groovy
dependencies {
    compile "com.github.Commit451:ehhttp:latest.version.here"
}
```

## Usage
You convert calls to the desired RxJava types:
```kotlin
val client = OkHttpClient.Builder()
    .build()

val request = Request.Builder()
        .url("http://www.example.com")
        .build()

val call = client.newCall(request)
//observable
val observable = call.toObservable()
//single
val single = call.toSingle()
//flowable
val flowable = call.toFlowable()
//completable
val completable = call.toCompletable()
```

## Supported types
- Observable: `toObservable()`
- Single: `toSingle()`
- Flowable: `toFlowable()` with `BackpressureStrategy.LATEST`
- Completable: `toCompletable()` which throws `HttpException` if HTTP error

## Acknowledgements
`HttpException` and `CallObservable` modified from [Retrofit](https://github.com/square/retrofit). Thanks to Square for being awesome.
License
--------

    Copyright 2017 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.