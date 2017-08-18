package com.commit451.ehhttp

import io.reactivex.*
import okhttp3.Call
import okhttp3.Response

fun Call.toObservable(): Observable<Response> {
    return CallObservable(this)
}

fun Call.toFlowable(): Flowable<Response> {
    return toObservable().toFlowable(BackpressureStrategy.LATEST)
}

fun Call.toSingle(): Single<Response> {
    return toObservable().singleOrError()
}

fun Call.toCompletable(): Completable {
    return toObservable().flatMapCompletable { response: Response ->
        if (response.isSuccessful) {
            Completable.complete()
        } else {
            Completable.error(HttpException(response))
        }
    }
}