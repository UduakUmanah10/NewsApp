package ps.room.shoppingapp.repository

import ps.room.shoppingapp.api.RetrofitInstance
import ps.room.shoppingapp.database.NewsDatabase
import ps.room.shoppingapp.dataclass.Article
import javax.inject.Inject

class NewsRepository@Inject constructor(private val database: NewsDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadLines(countrycode = countryCode, pageNumber)

    suspend fun searchForNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.search(searchquery = searchQuery, pageNumber = pageNumber)

    suspend fun upsert(article: Article) = database.getAtticleDao().upDateandInsert(article)

    fun getSavedNews() = database.getAtticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = database.getAtticleDao().DeleteArticle(article) }
