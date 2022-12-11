package net.pelozo.poketeams_challenge.modules.teamList.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.pelozo.domain.Region
import net.pelozo.domain.Team
import net.pelozo.poketeams_challenge.R
import net.pelozo.poketeams_challenge.databinding.ItemRegionBinding
import net.pelozo.poketeams_challenge.databinding.ItemTeamBinding
import net.pelozo.poketeams_challenge.modules.addTeam.constants.AddTeamConstants
import net.pelozo.poketeams_challenge.modules.regionList.view.adapters.RegionAdapter

class TeamAdapter(
    private val onEditClick: ((item: Team) -> Unit),
    private val onRemoveClick: ((item: Team) -> Unit),
) :
    ListAdapter<Team, TeamAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTeamBinding.bind(view)
        fun bind(item: Team) = with(binding) {
            tvTeamName.text = item.name
            tvDescription.text = item.description
            tvType.text = item.type
            tvNumber.text = item.number.toString()
            llPokemons.removeAllViews()
            item.pokemon?.forEach { pokemon ->
                val image = ImageView(binding.llPokemons.context)
                image.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                llPokemons.addView(image)
                Glide
                    .with(llPokemons.context)
                    .load(pokemon.imageUrl)
                    .apply(AddTeamConstants.glideOptions)
                    .into(image)
            }

            btEditTeam.setOnClickListener { onEditClick(item) }
            btRemoveTeam.setOnClickListener { onRemoveClick(item) }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}