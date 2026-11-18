package com.example.bookshelf.fake

import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book

class FakeBooksRepository : BooksRepository {
    override suspend fun getBooks(query: String): List<Book> =
        listOf(Book("id1", "Jazz Book", "https://books.google.com/img1"))
}
