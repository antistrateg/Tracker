package ru.developmentmobile.tracker.map.network

import android.content.Context
import android.util.TimeUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.developmentmobile.tracker.network.ResponseModel
import java.util.concurrent.TimeUnit

class NetworkClient(

) {

    fun <T> createClient(clazz: Class<T>): T = Retrofit.Builder()
        .baseUrl("https://development-mobile.ru/")
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(clazz)

    private fun getHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(2000,TimeUnit.MILLISECONDS)
        clientBuilder.readTimeout(2000,TimeUnit.MILLISECONDS)
        clientBuilder.writeTimeout(2000,TimeUnit.MILLISECONDS)
        return clientBuilder.build()
    }


    suspend fun <R> request(request: suspend () -> ResponseModel<R>): Response<R> {
        val responseModel = request()

        return if (responseModel.result == "success") {
            Response.success(responseModel.data!!)
        } else {
            responseModel.error?.let { Response.failure<R>(it.mapToResponseError()) }
                ?: Response.failure(ResponseError("error", "error"))
        }
    }

}
