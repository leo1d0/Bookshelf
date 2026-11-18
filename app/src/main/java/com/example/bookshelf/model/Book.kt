package com.example.bookshelf.model

/** Respuesta de búsqueda: GET volumes?q=... (solo nos interesan los ids). */
data class BookSearchResponse(
    val totalItems: Int = 0,
    val items: List<BookItem>? = null,
)

/** Elemento de la lista de resultados. */
data class BookItem(val id: String)

/** Respuesta del detalle de un libro: GET volumes/{id}. */
data class VolumeResponse(
    val id: String,
    val volumeInfo: VolumeInfo,
)

data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null,
)

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null,
)

/** Modelo que usa la UI: id, título y miniatura ya en https. */
data class Book(
    val id: String,
    val title: String,
    val thumbnailUrl: String?,
)
