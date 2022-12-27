package ps.room.shoppingapp.database

import androidx.lifecycle.LiveData
import androidx.room.* // ktlint-disable no-wildcard-imports
import ps.room.shoppingapp.dataclass.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upDateandInsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun DeleteArticle(article: Article)
}
