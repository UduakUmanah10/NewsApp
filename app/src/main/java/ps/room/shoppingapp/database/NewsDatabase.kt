package ps.room.shoppingapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ps.room.shoppingapp.dataclass.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(NewsConverters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getAtticleDao(): NewsDao
}
