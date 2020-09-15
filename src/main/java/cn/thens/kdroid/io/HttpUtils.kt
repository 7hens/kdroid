package cn.thens.kdroid.io

import cn.thens.kdroid.KDroid
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Part
import java.io.File
import java.lang.reflect.Type

object HttpUtils {
    fun retrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun partOf(formName: String, file: File): MultipartBody.Part {
        val mediaType = MediaType.get(FileUtils.getMimeType(file))
        val requestBody = RequestBody.create(mediaType, file)
        return MultipartBody.Part.createFormData(formName, file.name, requestBody)
    }

    private val okHttpClient: OkHttpClient by lazy {
        val logLevel = if (KDroid.debug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor(HttpLogger()).setLevel(logLevel))
                .build()
    }

    private class FileConverterFactory : Converter.Factory() {
        override fun requestBodyConverter(
                type: Type,
                parameterAnnotations: Array<Annotation>,
                methodAnnotations: Array<Annotation>,
                retrofit: Retrofit
        ): Converter<File, RequestBody>? {
            if (type != File::class.java) return null
            return Converter { file ->
                val part = parameterAnnotations.first { it is Part } as Part
                MultipartBody.Builder()
                        .addPart(partOf(part.value, file))
                        .build()
            }
        }
    }
}