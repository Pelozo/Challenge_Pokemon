package net.pelozo.poketeams_challenge.modules.selectPokemon.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.pelozo.domain.Pokemon
import net.pelozo.poketeams_challenge.R
import net.pelozo.poketeams_challenge.databinding.ItemPokemonBinding

class PokemonAdapter(
    private val onClick: ((item: Pokemon) -> Unit)
) :
    ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPokemonBinding.bind(view)
        fun bind(item: Pokemon) = with(binding) {
            tvPokemonName.text = item.name
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}