package ps.room.shoppingapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ps.room.shoppingapp.dataclass.Article
import ps.room.shoppingapp.dataclass.NewsResponse
import ps.room.shoppingapp.repository.NewsRepository
import ps.room.shoppingapp.util.NetworkResponse
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository

) : ViewModel() {

    private val breakingNews: MutableLiveData<NetworkResponse<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    val _breakingNews: LiveData<NetworkResponse<NewsResponse>> = breakingNews

    private val searchNews: MutableLiveData<NetworkResponse<NewsResponse>> = MutableLiveData()
    val _searchNews: LiveData<NetworkResponse<NewsResponse>> = searchNews
    var searchNewsPage = 1

    init {
        getBreakingNews("us")
        searchForNews("")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(NetworkResponse.Loading())
        val response: Response<NewsResponse> = newsRepository.getBreakingNews(countryCode, searchNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchForNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(NetworkResponse.Loading())
        val response: Response<NewsResponse> = newsRepository.searchForNews(searchQuery = searchQuery, breakingNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): NetworkResponse<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return NetworkResponse.successful(result)
            }
        }
        return NetworkResponse.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): NetworkResponse<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return NetworkResponse.successful(result)
            }
        }
        return NetworkResponse.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
