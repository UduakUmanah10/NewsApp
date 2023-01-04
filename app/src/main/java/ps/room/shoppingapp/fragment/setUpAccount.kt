package ps.room.shoppingapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ps.room.shoppingapp.data.User
import ps.room.shoppingapp.databinding.FragmentSetUpAccountBinding
import ps.room.shoppingapp.mvvm.registerationViewModel
import ps.room.shoppingapp.util.RegisterValidation
import ps.room.shoppingapp.util.Response
private val TAG = "RegistrationFragment"

@AndroidEntryPoint
class setUpAccount : Fragment() {

    private lateinit var binding: FragmentSetUpAccountBinding

    private val viewModel by viewModels<registerationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetUpAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            Login.setOnClickListener {
                val user = User(
                    createFirstName.text.toString().trim(),
                    createLastName.text.toString().trim(),
                    Email.text.toString().trim()
                )
                val password = password.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Response.Loading -> {
                        binding.Login.startAnimation()
                    }

                    is Response.Success -> {
                        Log.d("test", it.data.toString())
                        binding.Login.revertAnimation()

                        Toast.makeText(
                            requireActivity(),
                            "Account sucessfully created",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Response.Error -> {
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(
                            requireActivity(),
                            "${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.Login.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.Email.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.password.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }
    }
}
