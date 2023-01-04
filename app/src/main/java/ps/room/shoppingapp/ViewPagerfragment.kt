package ps.room.shoppingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ps.room.shoppingapp.databinding.FragmentViewPagerfragmentBinding
import ps.room.shoppingapp.onboarding.screens.firstScreen
import ps.room.shoppingapp.onboarding.screens.secondScreen
import ps.room.shoppingapp.onboarding.screens.thirdScreen

class ViewPagerfragment : Fragment() {
    private lateinit var binding: FragmentViewPagerfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerfragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf<Fragment>(
            firstScreen(),
            secondScreen(),
            thirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }
}
