package com.commit451.ehhttp

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CallToSingleTest {

    @Test
    fun callToSingleTest() {

        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200))
        server.start()

        val client = OkHttpClient.Builder()
                .build()

        val request = Request.Builder()
                .url(server.url(""))
                .build()

        val response = client.newCall(request).toSingle()
                .blockingGet()

        server.shutdown()

        Assert.assertNotNull(response)
    }

    @Test
    fun cancelSingleTest() {

        val server = MockWebServer()
        server.enqueue(MockResponse()
                .setBody("Hi there hows it going?")
                .throttleBody(1, 500, TimeUnit.MILLISECONDS))
        server.start()

        val client = OkHttpClient.Builder()
                .build()

        val request = Request.Builder()
                .url(server.url(""))
                .build()

        val countDownLatch = CountDownLatch(0)
        val call = client.newCall(request)
        var disposable: Disposable? = null
        call.toSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(object : SingleObserver<Response> {
                    override fun onError(e: Throwable) {
                        countDownLatch.countDown()
                    }

                    override fun onSuccess(t: Response) {
                        Assert.fail("Shouldn't succeed")
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }
                })
        disposable!!.dispose()
        countDownLatch.await(5, TimeUnit.SECONDS)

        server.shutdown()

        Assert.assertTrue(call.isCanceled)
    }
}