package com.example.bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BookshelfViewModel
import com.example.bookshelf.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineSmall)
                }
            )
        }
    ) { innerPadding ->
        val viewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
        HomeScreen(
            uiState = viewModel.uiState,
            query = viewModel.query,
            onQueryChange = viewModel::updateQuery,
            onSearch = viewModel::getBooks,
            retryAction = viewModel::getBooks,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}
