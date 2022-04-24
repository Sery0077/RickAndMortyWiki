package sery.vlasenko.rickandmortywiki.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_character.view.*
import sery.vlasenko.rickandmortywiki.data.dao.Character
import sery.vlasenko.rickandmortywiki.databinding.ItemCharacterBinding
import sery.vlasenko.rickandmortywiki.databinding.ItemLoadingBinding
import sery.vlasenko.rickandmortywiki.ui.base.adapter.BaseAdapter
import sery.vlasenko.rickandmortywiki.ui.base.adapter.RecyclerItem
import javax.inject.Inject

class AdapterCharacters @Inject constructor() : BaseAdapter() {

    companion object {
        const val ITEM = 0
        const val LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<RecyclerItem> {
        return when (viewType) {
            ITEM -> {
                val binding =
                    ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CharacterVH(binding)
            }
            LOADING -> {
                val binding =
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingVH(binding)
            }
            else -> throw IllegalStateException("Unknown item type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] == null) LOADING else ITEM
    }

    inner class CharacterVH<T : RecyclerItem>(itemView: ItemCharacterBinding) :
        BaseVH<T>(itemView) {

        override fun bind(item: T?) {
            (item as Character)
            with(itemView) {
                tv_gender.text = item.gender
                tv_name.text = item.name
                tv_species.text = item.species

                Glide.with(itemView.context)
                    .load(item.image)
                    .into(iv_avatar)
            }
        }
    }

    inner class LoadingVH<T : RecyclerItem>(itemView: ItemLoadingBinding) :
        BaseVH<T>(itemView) {

        override fun bind(item: T?) {}
    }
}