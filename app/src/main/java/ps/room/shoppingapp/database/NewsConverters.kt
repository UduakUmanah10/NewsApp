package ps.room.shoppingapp.database

import androidx.room.TypeConverter
import ps.room.shoppingapp.dataclass.Source

class NewsConverters {

    @TypeConverter
    fun sourceToString(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun stringToSource(name: String): Source {
        return Source(name, name)
    }
}
