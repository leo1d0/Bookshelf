package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import android.util.Log
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/** Estados posibles de la pantalla. */
sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    var uiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    /** Texto de búsqueda; por defecto el del codelab. */
    var query: String by mutableStateOf("jazz history")
        private set

    init {
        getBooks()
    }

    fun updateQuery(newQuery: String) {
        query = newQuery
    }

    fun getBooks() {
        val q = query.trim().ifEmpty { "jazz history" }
        viewModelScope.launch {
            uiState = BookshelfUiState.Loading
            uiState = try {
                BookshelfUiState.Success(booksRepository.getBooks(q))
            } catch (e: IOException) {
                Log.e(TAG, "Error de red", e)
                BookshelfUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "Error HTTP ${e.code()}: ${e.response()?.errorBody()?.string()}", e)
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        private const val TAG = "Bookshelf"
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                BookshelfViewModel(booksRepository = application.container.booksRepository)
            }
        }
    }
}
