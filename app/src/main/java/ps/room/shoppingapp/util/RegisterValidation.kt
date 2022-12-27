package ps.room.shoppingapp.util

sealed interface RegisterValidation {
    object Success : RegisterValidation
    data class Failed(val message: String) : RegisterValidation
}





data class RegistrationFieldState(
    val email: RegisterValidation,
    val password: RegisterValidation

)
