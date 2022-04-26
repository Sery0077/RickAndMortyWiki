package sery.vlasenko.rickandmortywiki.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import sery.vlasenko.rickandmortywiki.R
import sery.vlasenko.rickandmortywiki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ToolbarActivity {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.fragment_container_view)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        super.onPostCreate(savedInstanceState)
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }
}