package sery.vlasenko.rickandmortywiki.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sery.vlasenko.rickandmortywiki.R
import sery.vlasenko.rickandmortywiki.ui.characters.FragmentCharacters

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view_tag, FragmentCharacters::class.java, null, null)
            .commit()
    }
}