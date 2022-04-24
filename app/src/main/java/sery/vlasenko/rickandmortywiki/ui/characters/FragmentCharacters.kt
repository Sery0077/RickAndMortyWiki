package sery.vlasenko.rickandmortywiki.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import sery.vlasenko.rickandmortywiki.databinding.FragmentCharactersBinding
import sery.vlasenko.rickandmortywiki.ui.App
import sery.vlasenko.rickandmortywiki.ui.base.BaseBindingFragment
import sery.vlasenko.rickandmortywiki.ui.characters.adapter.AdapterCharacters

class FragmentCharacters : BaseBindingFragment<FragmentCharactersBinding, ViewModelCharacters>(
    FragmentCharactersBinding::inflate
) {

    override val model: ViewModelCharacters by viewModels {
        viewModelFactory
    }

    private val adapter = AdapterCharacters()

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()

        model.state.observe(viewLifecycleOwner) {
            processState(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecycler() {
        with(binding.rvCharacters) {
            setHasFixedSize(true)
            adapter = this@FragmentCharacters.adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1)) {
                        model.onEndScroll()
                    }
                }
            })
        }
    }

    private fun processState(state: CharactersListState) {
        when (state) {
            is CharactersListState.DataLoadError -> {
                val data = state.message.toString()

                Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
            }
            is CharactersListState.DataLoaded -> {
                val data = state.data

                adapter.submitList(data)
            }
            is CharactersListState.DataLoading -> {

            }
        }
    }
}