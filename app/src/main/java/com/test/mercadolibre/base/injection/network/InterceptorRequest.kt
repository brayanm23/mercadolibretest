package com.test.mercadolibre.base.injection.network

import com.test.mercadolibre.base.extensions.errorMessage
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.BufferedSource
import okio.GzipSource
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class InterceptorRequest @JvmOverloads constructor(private val logger: Logger = Logger.DEFAULT) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        var requestStartMessage = ("--> "
                + request.method()
                + ' '.toString() + request.url()
                + if (connection != null) " " + connection.protocol() else "")
        if (hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        logger.log(requestStartMessage)

        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody!!.contentType() != null) {
                logger.log("Content-Type: " + requestBody.contentType()!!)
            }
            if (requestBody.contentLength() != -1L) {
                logger.log("Content-Length: " + requestBody.contentLength())
            }
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logger.log("<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val headers = response.headers()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        logger.log("<-- "
                + response.code()
                + (if (response.message().isEmpty()) "" else ' ' + response.message())
                + ' '.toString() + response.request().url()
                + " (" + tookMs + "ms" + ')'.toString())

        val info = response.request().url().url().toString()
        var source: BufferedSource? = null
        try {
            source = responseBody.source()
        } catch (catched: Throwable) {
            logger.log("Error while trying to parse responseBody")
            logger.log(catched.errorMessage)
            logger.log("*******")
            logger.log(info)
            logger.log("*******")
            logger.log("<-- END HTTP - With Exception")
        } finally {
            logger.log("*")
        }

        source?.let {
            logger.log("Start of Source - Request")
            try {
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            } catch (catched: Throwable) {
                logger.log("Error while trying to process source request")
                logger.log(catched.errorMessage)
                logger.log("*******")
                logger.log(info)
                logger.log("*******")
                logger.log("<-- END HTTP - With Exception")
            } finally {
                logger.log("*")
            }
            logger.log("End of Source - Request")

            try {
                logger.log("Start of Source -  Buffer")
                var buffer = source.buffer()
                var gzippedLength: Long? = null
                headers.get("Content-Encoding")?.let {
                    if ("gzip".equals(headers.get("Content-Encoding")!!, ignoreCase = true)) {
                        gzippedLength = buffer.size()
                        var gzippedResponseBody: GzipSource? = null
                        try {
                            gzippedResponseBody = GzipSource(buffer.clone())
                            buffer = Buffer()
                            buffer.writeAll(gzippedResponseBody)
                        } finally {
                            gzippedResponseBody?.close()
                        }
                    }
                }


                var charset: Charset? = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                logger.log("End of Source -  Buffer")

                if (!isPlaintext(buffer)) {
                    logger.log("")
                    logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
                    return response
                }

                if (contentLength != 0L) {
                    logger.log(buffer.clone().readString(charset!!))
                }

                if (gzippedLength != null) {
                    logger.log("<-- END HTTP (" + buffer.size() + "-byte, "
                            + gzippedLength + "-gzipped-byte body)")
                } else {
                    logger.log("<-- END HTTP (" + buffer.size() + "-byte body)")
                }

            } catch (catched: Throwable) {
                logger.log("Error while trying to process source buffer")
                logger.log(catched.errorMessage)
                logger.log("*******")
                logger.log(info)
                logger.log("*******")
                logger.log("<-- END HTTP - With Exception")
            } finally {
                logger.log("*")
            }
        }
        return chain.proceed(request)
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false // Truncated UTF-8 sequence.
            }

        }
    }

    interface Logger {
        fun log(message: String)

        companion object {
            /** A [Logger] defaults output appropriate for the current platform.  */
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    Platform.get().log(Platform.INFO, message, null)
                }
            }
        }
    }
}
