package ps.room.shoppingapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ps.room.shoppingapp.R
import ps.room.shoppingapp.activity.NewsActivity
import ps.room.shoppingapp.databinding.FragmentLoginBinding
import ps.room.shoppingapp.dialouge.setUpBottomDialog
import ps.room.shoppingapp.mvvm.LoginViewModel
import ps.room.shoppingapp.util.Response

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    val LoginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_setUpAccount)
        }

        binding.apply {
            Login.setOnClickListener {
                val email = email.text.toString().trim()
                val password = loginpassword.text.toString()
                LoginViewModel.loginWithEmailAndPassword(email, password)
            }
        }

        binding.forgotPassword.setOnClickListener {
            setUpBottomDialog { email ->
                LoginViewModel.resetPassword(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            LoginViewModel.resetPasswordFlow.collect {
                when (it) {
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        Snackbar.make(
                            requireView(),
                            "the reset link has been sent to your Email",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is Response.Error -> {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            LoginViewModel.login.collect {
                when (it) {
                    is Response.Loading -> {
                        binding.Login.startAnimation()
                    }
                    is Response.Success -> {
                        binding.Login.revertAnimation()
                        val intent = Intent(requireActivity(), NewsActivity::class.java).also {
                            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        startActivity(intent)
                    }
                    is Response.Error -> {
                        binding.Login.revertAnimation()
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}
