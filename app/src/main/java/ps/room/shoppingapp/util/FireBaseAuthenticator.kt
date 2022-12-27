package ps.room.shoppingapp.util // ktlint-disable filename

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ps.room.shoppingapp.data.User
import javax.inject.Inject

class FireBaseAuthenticatorcon @Inject constructor(
    private val dataBase: FirebaseFirestore,
    private val FirebaseAuth: FirebaseAuth
) : Authentication {

    override fun createAccountWithEmailAndPassword(
        user: User,
        password: String,
        collectInputForRegisterFlow: (Response<User>) -> Unit
    ) {
        FirebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                    AuthenticationResult ->
                AuthenticationResult.user?.let {
                    saveUserInfo(it.uid, user) { responseFromSavedInfo ->
                        collectInputForRegisterFlow(responseFromSavedInfo)
                    }
                }
            }
            .addOnFailureListener {
                collectInputForRegisterFlow(Response.Error(it.message.toString()))
            }
    }

    override fun saveUserInfo(
        userId: String,
        user: User,
        exposeResponsetoAuthentication:
            (Response<User>) -> Unit
    ) {
        dataBase
            .collection(constants.USER_COLLECTION)
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                exposeResponsetoAuthentication(Response.Success(user))
            }
            .addOnFailureListener {
                exposeResponsetoAuthentication(Response.Error(it.message.toString()))
            }
    }
}
