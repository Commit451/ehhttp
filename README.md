# ehHttp
OkHttp, eh? Converts OkHttp `Call`s to RxJava types

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.commit451/ehhttp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.commit451/ehhttp)

## Gradle
```groovy
dependencies {
    implementation("com.commit451:ehhttp:latest.release.here")
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
// Observable
val observable = call.toObservable()
// Single
val single = call.toSingle()
// Flowable
val flowable = call.toFlowable()
// Completable
val completable = call.toCompletable()
```
If you need to, you can also call from Java:
```java
Single<Response> single = EhHttp.toSingle(call);
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

    Copyright 2022 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
