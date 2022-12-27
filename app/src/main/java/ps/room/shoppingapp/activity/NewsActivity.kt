package ps.room.shoppingapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ps.room.shoppingapp.R
import ps.room.shoppingapp.databinding.ActivityNewsActivityBinding
import ps.room.shoppingapp.mvvm.NewsViewModel

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsActivityBinding
    val viewModel by viewModels<NewsViewModel>()
    lateinit var newsViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsViewModel = viewModel
        val navController = findNavController(R.id.shoppingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}
