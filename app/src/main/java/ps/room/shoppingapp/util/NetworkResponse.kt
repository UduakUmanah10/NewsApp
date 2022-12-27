package ps.room.shoppingapp.util

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null

) {
    class Error<T>(message: String, data: T? = null) : NetworkResponse<T>(data, message)
    class Loading<T> : NetworkResponse<T>()
    class successful<T>(data: T) : NetworkResponse<T>(data)
}
