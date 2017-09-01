package com.commit451.ehhttp

import io.reactivex.*
import okhttp3.Call
import okhttp3.Response

/**
 * Converts the call to an observable which will emit the response, then complete
 */
fun Call.toObservable(): Observable<Response> {
    return CallObservable(this)
}

/**
 * Converts the call to a flowable which will emit the latest response, then complete
 */
fun Call.toFlowable(): Flowable<Response> {
    return toObservable().toFlowable(BackpressureStrategy.LATEST)
}

/**
 * Converts the call to a single which will emit the response or an error
 */
fun Call.toSingle(): Single<Response> {
    return toObservable().singleOrError()
}

/**
 * Converts the call to a completable which will complete, or fail with an exception. See [HttpException]
 */
fun Call.toCompletable(): Completable {
    return toObservable().flatMapCompletable { response: Response ->
        if (response.isSuccessful) {
            Completable.complete()
        } else {
            Completable.error(HttpException(response))
        }
    }
}