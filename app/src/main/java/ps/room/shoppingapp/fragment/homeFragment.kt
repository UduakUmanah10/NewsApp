package ps.room.shoppingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import ps.room.shoppingapp.R
import ps.room.shoppingapp.databinding.FragmentHomeBinding

class homeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lottiAnimation.playAnimation()
        binding.startShopping.setOnClickListener {
            binding.lottiAnimation.pauseAnimation()
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }
    }
}
