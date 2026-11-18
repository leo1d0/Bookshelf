package com.example.bookshelf.fake

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BookSearchResponse
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.model.VolumeResponse

object FakeDataSource {
    val searchResponse = BookSearchResponse(
        totalItems = 2,
        items = listOf(BookItem("id1"), BookItem("id2"))
    )
    val volumes = mapOf(
        "id1" to VolumeResponse(
            "id1", VolumeInfo("Jazz Book", null, ImageLinks(thumbnail = "http://books.google.com/img1"))
        ),
        "id2" to VolumeResponse(
            "id2", VolumeInfo("History Book", null, ImageLinks(thumbnail = "http://books.google.com/img2"))
        ),
    )
}
