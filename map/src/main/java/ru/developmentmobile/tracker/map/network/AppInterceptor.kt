package ru.developmentmobile.tracker.map.network

import android.annotation.SuppressLint
import com.google.gson.Gson
import okhttp3.*
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.developmentmobile.tracker.network.ErrorModel
import ru.developmentmobile.tracker.network.ResponseModel

class AppInterceptor constructor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = responseBuilder(chain)

        return try {
            val response = chain.proceed(request)
            if (response.code != SUCCESS_CODE) createExceptionResponse(response, request)
            else response
        } catch (e: Exception) {
            createExceptionResponse(e, request)
        }
    }

    @SuppressLint("DefaultLocale")
    private fun createExceptionResponse(response: okhttp3.Response, request: Request): okhttp3.Response {
        val body = response.peekBody(MAX_BODY_SIZE)
        if (!body.contentType()?.subtype?.toLowerCase().equals(JSON_TYPE)) {
            val responseModel = ResponseModel<Nothing>().apply {
                result = "error"
                error = ErrorModel().apply {
                    type = "${"error"}-${response.code}"
                    details = body.source().readUtf8LineStrict()
                }
            }

            return okhttp3.Response.Builder().request(request)
                .protocol(Protocol.HTTP_2)
                .code(SUCCESS_CODE)
                .message("error")
                .body(Gson().toJson(responseModel).toResponseBody(request.body?.contentType())).build()
        }

        return okhttp3.Response.Builder().request(response.request)
            .protocol(Protocol.HTTP_2)
            .code(SUCCESS_CODE)
            .message("${"error"}-${response.code}")
            .body(body).build()
    }

    private fun createExceptionResponse(e: Exception, request: Request): okhttp3.Response {
        val responseModel = ResponseModel<Nothing>().apply {
            result = "error"
            error = ErrorModel().apply {
                type = "unprocessable"
                details = e.message ?: "Unknown error"
            }
        }
        return Response.Builder().request(request)
            .protocol(Protocol.HTTP_2)
            .code(SUCCESS_CODE)
            .message("error")
            .body(Gson().toJson(responseModel).toResponseBody(request.body?.contentType())).build()
    }

    private fun responseBuilder(chain: Interceptor.Chain): Request {
        val request = chain.request()
        val urlBuilder = request.url.newBuilder()
        val url = urlBuilder.build()
        val builder = request.newBuilder().url(url)
        return builder.build()
    }

    companion object {
        private const val MAX_BODY_SIZE = java.lang.Long.MAX_VALUE - 1
        private const val JSON_TYPE = "json"

        //Response Code
        private const val SUCCESS_CODE = 200
    }
}