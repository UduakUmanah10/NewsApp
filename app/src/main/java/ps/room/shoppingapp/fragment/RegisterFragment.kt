package ps.room.shoppingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ps.room.shoppingapp.R
import ps.room.shoppingapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            btnSplashRegister.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_setUpAccount) }
            btnSplashSignIn.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_loginFragment) }
        }
    }
}
