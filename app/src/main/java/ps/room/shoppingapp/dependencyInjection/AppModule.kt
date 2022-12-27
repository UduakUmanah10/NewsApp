package ps.room.shoppingapp.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ps.room.shoppingapp.database.NewsDao
import ps.room.shoppingapp.database.NewsDatabase
import ps.room.shoppingapp.util.Authentication
import ps.room.shoppingapp.util.FireBaseAuthenticatorcon
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("firebase")
    fun provideFireBaseAuthentication(fire: FirebaseFirestore, fireBaseAuth: FirebaseAuth): Authentication {
        return FireBaseAuthenticatorcon(fire, fireBaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java, "news").build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.getAtticleDao()
    }
}
