package com.commit451.ehhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test

class CallToObservableTest {

    @Test
    fun callToObservable() {

        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200))
        server.start()

        val client = OkHttpClient.Builder()
                .build()

        val request = Request.Builder()
                .url(server.url(""))
                .build()

        val response = client.newCall(request).toObservable()
                .blockingFirst()

        server.shutdown()

        Assert.assertNotNull(response)
    }
}