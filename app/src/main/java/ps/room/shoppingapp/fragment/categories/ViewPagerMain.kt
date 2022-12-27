package ps.room.shoppingapp.fragment.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ps.room.shoppingapp.R
import ps.room.shoppingapp.databinding.ViewPagerMainBinding

class ViewPagerMain : Fragment(R.layout.view_pager_main) {
    private lateinit var binding: ViewPagerMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewPagerMainBinding.inflate(inflater, container, false)
        return binding.root
    }
}
