package com.example.bookshelf.fake

import com.example.bookshelf.model.BookSearchResponse
import com.example.bookshelf.model.VolumeResponse
import com.example.bookshelf.network.BookshelfApiService

/** Servicio falso: devuelve datos sin llamar a la API real de Google Books. */
class FakeBookshelfApiService : BookshelfApiService {
    val requestedIds = mutableListOf<String>()
    override suspend fun searchBooks(query: String): BookSearchResponse = FakeDataSource.searchResponse
    override suspend fun getBook(id: String): VolumeResponse {
        requestedIds.add(id)
        return FakeDataSource.volumes.getValue(id)
    }
}
