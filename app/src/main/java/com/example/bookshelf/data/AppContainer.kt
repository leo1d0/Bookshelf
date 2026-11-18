package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import com.example.bookshelf.BuildConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Contenedor de dependencias de la app (inyección de dependencias manual). */
interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"

    /**
     * Cliente HTTP: si hay clave de API, la añade a cada petición (&key=...).
     * Así cada usuario usa su propia cuota y se evita el error 429.
     */
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        // Tiempos de espera más amplios para redes lentas (el valor por defecto es 10 s).
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request()
            val key = BuildConfig.BOOKS_API_KEY
            if (key.isBlank()) {
                chain.proceed(request)
            } else {
                val url = request.url.newBuilder().addQueryParameter("key", key).build()
                chain.proceed(request.newBuilder().url(url).build())
            }
        }
        .build()

    /** Retrofit con Gson para convertir el JSON a objetos Kotlin. */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(httpClient)
        .build()

    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }
}
