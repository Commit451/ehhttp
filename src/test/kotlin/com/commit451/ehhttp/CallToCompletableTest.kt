package com.commit451.ehhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test

class CallToCompletableTest {

    @Test
    fun callToCompletable() {

        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200))
        server.start()

        val client = OkHttpClient.Builder()
                .build()

        val request = Request.Builder()
                .url(server.url(""))
                .build()

        val throwable = client.newCall(request).toCompletable()
                .blockingGet()

        server.shutdown()

        Assert.assertNull(throwable)
    }

    @Test
    fun callToCompletableFail() {

        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(400))
        server.start()

        val client = OkHttpClient.Builder()
                .build()

        val request = Request.Builder()
                .url(server.url(""))
                .build()

        val throwable = client.newCall(request).toCompletable()
                .blockingGet()

        server.shutdown()

        Assert.assertNotNull(throwable)
        Assert.assertTrue(throwable is HttpException)
        Assert.assertEquals(400, (throwable as HttpException).code())
    }

}