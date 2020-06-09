/*
 * Copyright (C) 2016 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package com.commit451.ehhttp

import okhttp3.Response


/** Exception for an unexpected, non-2xx HTTP response.  */
class HttpException(@Transient private val response: Response) : RuntimeException(getMessage(response)) {

    companion object {
        private fun getMessage(response: Response): String {
            checkNotNull(response, "response == null")
            return "HTTP " + response.code + " " + response.message
        }

        private fun <T> checkNotNull(`object`: T?, message: String): T {
            if (`object` == null) {
                throw NullPointerException(message)
            }
            return `object`
        }
    }

    private val code: Int = response.code
    private val responseMessage: String = response.message

    /** HTTP status code.  */
    fun code(): Int {
        return code
    }

    /** HTTP status message.  */
    fun message(): String {
        return responseMessage
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    fun response(): Response {
        return response
    }
}