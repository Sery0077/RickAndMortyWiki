package sery.vlasenko.rickandmortywiki.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import sery.vlasenko.rickandmortywiki.R
import sery.vlasenko.rickandmortywiki.databinding.FragmentCharactersBinding
import sery.vlasenko.rickandmortywiki.ui.App
import sery.vlasenko.rickandmortywiki.ui.base.BaseBindingFragment
import sery.vlasenko.rickandmortywiki.ui.characters.adapter.AdapterCharacters
import sery.vlasenko.rickandmortywiki.utils.Keys
import sery.vlasenko.rickandmortywiki.utils.SnackBarHelper
import sery.vlasenko.rickandmortywiki.data.dao.Character

class FragmentCharacters :
    BaseBindingFragment<FragmentCharactersBinding, ViewModelCharacters>(FragmentCharactersBinding::inflate),
    AdapterCharacters.ClickListener {

    override val model: ViewModelCharacters by viewModels {
        viewModelFactory
    }

    private val adapter = AdapterCharacters(this)

    private var errorSnackBar: Snackbar? = null

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

    override fun onPause() {
        errorSnackBar?.dismiss()
        model.onPause()
        super.onPause()
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

                errorSnackBar = SnackBarHelper.errorSnackBar(binding.root) {
                    model.onErrorClicked()
                }

                errorSnackBar?.show()
            }
            is CharactersListState.DataLoaded -> {
                errorSnackBar?.dismiss()

                val data = state.data
                adapter.submitList(data)

                binding.progressBar.visibility = View.GONE
                binding.rvCharacters.visibility = View.VISIBLE
            }
            is CharactersListState.DataLoading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvCharacters.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(character: Character) {
        val args = Bundle().apply {
            putParcelable(Keys.CHARACTER, character)
        }
        findNavController().navigate(R.id.action_fragmentCharacters_to_fragmentCharacterInfo, args)
    }
}