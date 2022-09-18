package com.hasib.basic_paging.data

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject

private val firstArticleCreatedTime = LocalDateTime.now()

/**
 * Repository class that mimics fetching [Article] instances from an asynchronous source.
 */
@ViewModelScoped
class ArticleRepository @Inject constructor() {

    fun articlePagingSource() = ArticlePagingSource()
}
