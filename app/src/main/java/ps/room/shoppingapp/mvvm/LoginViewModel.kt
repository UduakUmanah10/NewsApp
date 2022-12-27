package ps.room.shoppingapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ps.room.shoppingapp.util.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val fireBaseAuth: FirebaseAuth
) : ViewModel() {

    private val _login = MutableSharedFlow<Response<FirebaseUser>>()
    val login = _login.asSharedFlow()

    private val _resetPasswordFlow = MutableSharedFlow<Response<String>>()
    val resetPasswordFlow = _resetPasswordFlow.asSharedFlow()

    fun loginWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch { _login.emit(Response.Loading()) }
        fireBaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                viewModelScope.launch {
                    it.user?.let {
                        _login.emit(Response.Success(it))
                    }
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Response.Error(it.message.toString()))
                }
            }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch { _resetPasswordFlow.emit(Response.Loading()) }
        fireBaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _resetPasswordFlow.emit(Response.Success(email))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _resetPasswordFlow.emit(Response.Error(it.message.toString()))
                }
            }
    }
}
