package sery.vlasenko.rickandmortywiki.ui.characterinfo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.databinding.FragmentCharacterInfoBinding
import sery.vlasenko.rickandmortywiki.ui.App
import sery.vlasenko.rickandmortywiki.ui.base.BaseBindingFragment
import sery.vlasenko.rickandmortywiki.utils.Keys

class FragmentCharacterInfo :
    BaseBindingFragment<FragmentCharacterInfoBinding, ViewModelCharacterInfo>
        (FragmentCharacterInfoBinding::inflate) {

    override val model: ViewModelCharacterInfo by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        val characterId = arguments?.getInt(Keys.CHARACTER_ID_KEY)
            ?: throw IllegalStateException("Character id must not be null")
        model.onAttach(characterId)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model.state.observe(viewLifecycleOwner) {
            processState(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun processState(state: CharacterInfoState) {
        when (state) {
            is CharacterInfoState.DataLoadError -> {
                val data = state.message.toString()

                Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
            }
            is CharacterInfoState.DataLoaded -> {
                bindViews(state.data)
            }
            is CharacterInfoState.DataLoading -> {

            }
        }
    }

    private fun bindViews(character: Character) {
        with(binding) {
            Glide.with(requireContext())
                .load(character.image)
                .into(ivAvatar)

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