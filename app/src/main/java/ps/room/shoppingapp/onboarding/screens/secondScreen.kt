package ps.room.shoppingapp.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ps.room.shoppingapp.R
import ps.room.shoppingapp.databinding.FragmentSecondScreenBinding

class secondScreen : Fragment() {
    private lateinit var binding: FragmentSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.lottiAnimation.playAnimation()

        binding.next2.setOnClickListener {
            binding.lottiAnimation.pauseAnimation()
            viewpager?.currentItem = 2
        }
    }
}
