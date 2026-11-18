package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.fake.FakeBookshelfApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBooksRepositoryTest {
    @Test
    fun networkBooksRepository_getBooks_returnsBooksWithHttpsThumbnails() = runTest {
        val service = FakeBookshelfApiService()
        val books = NetworkBooksRepository(service).getBooks("jazz history")

        assertEquals(listOf("id1", "id2"), books.map { it.id })
        assertEquals(listOf("Jazz Book", "History Book"), books.map { it.title })
        assertEquals(
            listOf("https://books.google.com/img1", "https://books.google.com/img2"),
            books.map { it.thumbnailUrl }
        )
        // Se pidió el detalle de cada libro, en orden
        assertEquals(listOf("id1", "id2"), service.requestedIds)
    }
}
