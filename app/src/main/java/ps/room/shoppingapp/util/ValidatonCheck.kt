package ps.room.shoppingapp.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation {
    if (email.isEmpty()) {
        return RegisterValidation.Failed("Email cannot be Empty")
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return RegisterValidation.Failed("Wrong Email Format")
    }

    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation {
    if (password.isEmpty()) {
        return RegisterValidation.Failed("password cannot be Empty")
    }

    if (password.length < 6) {
        return RegisterValidation.Failed("password Length  must Be Greater than 6 Characters")
    }

    return RegisterValidation.Success
}
