package com.example.bookshelf.network

import com.example.bookshelf.model.BookSearchResponse
import com.example.bookshelf.model.VolumeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** Servicio Retrofit para la API de Google Books. */
interface BookshelfApiService {
    /** Busca libros: https://www.googleapis.com/books/v1/volumes?q=jazz+history */
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookSearchResponse

    /** Información de un libro concreto: https://www.googleapis.com/books/v1/volumes/<id> */
    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): VolumeResponse
}
