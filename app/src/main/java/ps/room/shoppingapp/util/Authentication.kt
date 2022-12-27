package ps.room.shoppingapp.util

import ps.room.shoppingapp.data.User

interface Authentication {

    fun createAccountWithEmailAndPassword(
        user: User,
        password: String,
        collectInputForRegisterFlow: (Response<User>) -> Unit
    ) {}

    fun saveUserInfo(
        userId: String,
        user: User,
        exposeResponsetoAuthentication:
            (Response<User>) -> Unit
    ) {}
}
