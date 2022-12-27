package ps.room.shoppingapp.newsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ps.room.shoppingapp.R
import ps.room.shoppingapp.adapters.HomeViewPagerAdapter
import ps.room.shoppingapp.databinding.FragmentNewshomeBinding
import ps.room.shoppingapp.fragment.categories.ViewPagerMain
import ps.room.shoppingapp.fragment.testpager.first

class NewsHomeFragment : Fragment(
    R.layout.fragment_newshome
) {
    private lateinit var binding: FragmentNewshomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewshomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerFragment = arrayListOf<Fragment>(
            first(),
            ViewPagerMain()
        )

        val viwShoppingAdapter = HomeViewPagerAdapter(
            viewPagerFragment,
            childFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = viwShoppingAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "main"
                1 -> tab.text = "Chair"
                2 -> tab.text = "CupBoard"
                3 -> tab.text = "Table"
                4 -> tab.text = "Accessory"
                5 -> tab.text = "Furniture"
            }
        }.attach()
    }
}
