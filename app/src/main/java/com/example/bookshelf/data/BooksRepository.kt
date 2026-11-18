package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BookshelfApiService

/** Repositorio que obtiene los libros (permite cambiar la fuente de datos). */
interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
}

/** Implementación de red que usa el servicio Retrofit. */
class NetworkBooksRepository(
    private val bookshelfApiService: BookshelfApiService,
) : BooksRepository {

    override suspend fun getBooks(query: String): List<Book> {
        val ids = bookshelfApiService.searchBooks(query).items.orEmpty().map { it.id }
        // Peticiones secuenciales en la misma corrutina, como pide el codelab:
        // cada una empieza cuando termina la anterior. Se guardan en una MutableList.
        val books = mutableListOf<Book>()
        for (id in ids) {
            val volume = bookshelfApiService.getBook(id)
            books.add(
                Book(
                    id = volume.id,
                    title = volume.volumeInfo.title ?: "",
                    // Las URL vienen en http: se cambian a https o Coil no las muestra.
                    thumbnailUrl = volume.volumeInfo.imageLinks?.thumbnail
                        ?.replace("http://", "https://"),
                )
            )
        }
        return books
    }
}
