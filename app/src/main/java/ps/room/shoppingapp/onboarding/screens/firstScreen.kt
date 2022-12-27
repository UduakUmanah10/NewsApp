package ps.room.shoppingapp.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ps.room.shoppingapp.R
import ps.room.shoppingapp.databinding.FragmentFirstScreenBinding

class firstScreen : Fragment() {

    private lateinit var binding: FragmentFirstScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        // val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        // val viewpager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        // view.next.setOnClickListener {
        //     viewpager?.currentItem = 1
        //  }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.next.setOnClickListener {
            viewpager?.currentItem = 1
        }
    }
}
