package net.pelozo.poketeams_challenge.modules.addTeam.constants

import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import net.pelozo.poketeams_challenge.R

class AddTeamConstants {
    companion object{
        const val MIN_POKEMONS_PER_TEAM = 3
        const val MAX_POKEMONS_PER_TEAM = 6

        val glideOptions: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .priority(Priority.HIGH)
            .error(R.drawable.ic_arrow_right)
    }
}