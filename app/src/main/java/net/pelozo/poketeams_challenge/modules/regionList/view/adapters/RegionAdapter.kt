package net.pelozo.poketeams_challenge.modules.regionList.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets.Side.all
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.pelozo.domain.Region
import net.pelozo.poketeams_challenge.R
import net.pelozo.poketeams_challenge.databinding.ItemRegionBinding
import net.pelozo.poketeams_challenge.modules.regionList.constants.RegionListConstants.Companion.ALL_TEAMS_ID

class RegionAdapter(
    private val onClick: ((item: Region) -> Unit)
) :
    ListAdapter<Region, RegionAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRegionBinding.bind(view)
        fun bind(item: Region) = with(binding) {
            tvRegionName.text =
                if (item.url == ALL_TEAMS_ID) root.context.getString(R.string.all) else item.name
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Region>() {
    override fun areItemsTheSame(oldItem: Region, newItem: Region): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Region, newItem: Region): Boolean {
        return oldItem == newItem
    }
}