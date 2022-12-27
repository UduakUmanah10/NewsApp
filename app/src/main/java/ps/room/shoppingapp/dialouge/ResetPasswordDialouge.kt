package ps.room.shoppingapp.dialouge

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ps.room.shoppingapp.R

fun Fragment.setUpBottomDialog(
    onSendClick: (String) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.resetpassword, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val email = view.findViewById<EditText>(R.id.dialougeEditText)
    val cancelButton = view.findViewById<Button>(R.id.dialougeCancelButton)
    val resetButton = view.findViewById<Button>(R.id.dialougeSendResetPassword)

    resetButton.setOnClickListener {
        val dialogueEmail = email.text.toString().trim()
        onSendClick(dialogueEmail)
        dialog.dismiss()
    }

    cancelButton.setOnClickListener {
        dialog.dismiss()
    }
}
