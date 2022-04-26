package sery.vlasenko.rickandmortywiki.ui.characterinfo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import sery.vlasenko.rickandmortywiki.R
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.databinding.FragmentCharacterInfoBinding
import sery.vlasenko.rickandmortywiki.ui.App
import sery.vlasenko.rickandmortywiki.ui.ToolbarActivity
import sery.vlasenko.rickandmortywiki.ui.base.BaseBindingFragment
import sery.vlasenko.rickandmortywiki.utils.Keys
import sery.vlasenko.rickandmortywiki.utils.SnackBarHelper

class FragmentCharacterInfo :
    BaseBindingFragment<FragmentCharacterInfoBinding, ViewModelCharacterInfo>
        (FragmentCharacterInfoBinding::inflate) {

    override val model: ViewModelCharacterInfo by viewModels {
        viewModelFactory
    }

    private var errorSnackBar: Snackbar? = null

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)

        val character: Character = arguments?.getParcelable(Keys.CHARACTER)
            ?: throw IllegalStateException("Character must not be null")

        model.onAttach(character.id.toInt())

        if (context is ToolbarActivity) {
            context.setToolbarTitle(character.name)
        }

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model.state.observe(viewLifecycleOwner) {
            processState(it)
        }

        val character: Character = arguments?.getParcelable(Keys.CHARACTER)
            ?: throw IllegalStateException("Character must not be null")

        firstInitViews(character)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        errorSnackBar?.dismiss()
        model.onPause()
        super.onPause()
    }

    private fun processState(state: CharacterInfoState) {
        when (state) {
            is CharacterInfoState.DataLoadError -> {
                val data = state.message.toString()

                Toast.makeText(context, data, Toast.LENGTH_SHORT).show()

                errorSnackBar = SnackBarHelper.errorSnackBar(binding.root) {
                    model.onErrorClicked()
                }

                errorSnackBar?.show()
            }
            is CharacterInfoState.DataLoaded -> {
                errorSnackBar?.dismiss()

                bindViews(state.data)

                binding.progressBar.visibility = View.GONE
                binding.content.visibility = View.VISIBLE
            }
            is CharacterInfoState.DataLoading -> {
                errorSnackBar?.dismiss()

                binding.progressBar.visibility = View.VISIBLE
                binding.content.visibility = View.GONE
            }
        }
    }

    private fun firstInitViews(character: Character) {
        with(binding) {
            Glide.with(requireContext())
                .load(character.image)
                .placeholder(R.drawable.character_placeholder)
                .into(ivAvatar)

            tvName.text = character.name
            bindGenderAndSpecies(character)
        }
    }

    private fun bindViews(character: Character) {
        with(binding) {
            tvName.text = character.name
            tvEpisodesCount.text = character.episode.size.toString()
            tvLastLocation.text = character.location.name
            tvStatus.text = character.status

            bindGenderAndSpecies(character)
        }
    }

    private fun bindGenderAndSpecies(character: Character) {
        binding.tvGenderAndSpecies.text = "${character.gender}, ${character.species}"
    }
}