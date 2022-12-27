package ps.room.shoppingapp.mvvm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import ps.room.shoppingapp.data.User
import ps.room.shoppingapp.util.Authentication
import ps.room.shoppingapp.util.RegisterValidation
import ps.room.shoppingapp.util.RegistrationFieldState
import ps.room.shoppingapp.util.Response
import ps.room.shoppingapp.util.validateEmail
import ps.room.shoppingapp.util.validatePassword
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class registerationViewModel @Inject constructor(
    @Named("firebase")
    private val firebaseAuthentication: Authentication

) : ViewModel() {

    private val _register = MutableStateFlow<Response<User>>(Response.InitialState())
    var register: Flow<Response<User>> = _register

    private val _validation = Channel<RegistrationFieldState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User, password: String) {
        checkValidation(user, password)
        if (checkValidation(user, password)) {
            runBlocking { _register.emit(Response.Loading()) }
            firebaseAuthentication.createAccountWithEmailAndPassword(user, password) {
                _register.value = it
            }
        } else {
            val registerFieldState = RegistrationFieldState(
                validateEmail(user.email),
                validatePassword(password)
            )
            runBlocking {
                _validation.send(registerFieldState)
            }
        }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)

        return emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
    }
}
