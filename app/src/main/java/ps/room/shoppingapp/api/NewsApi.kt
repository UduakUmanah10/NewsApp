package ps.room.shoppingapp.api

import ps.room.shoppingapp.constants.constants.API_KEY
import ps.room.shoppingapp.dataclass.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadLines(
        @Query("country")
        countrycode: String = "us",

        @Query("page")
        pageNumber: Int = 1,

        @Query("apiKey")
        apiKey: String = API_KEY,

        @Query("category")
        category: String = "sports"

    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun search(
        @Query("q")
        searchquery: String,

        @Query("page")
        pageNumber: Int = 1,

        @Query("apiKey")
        apiKey: String = API_KEY

    ): Response<NewsResponse>
}
