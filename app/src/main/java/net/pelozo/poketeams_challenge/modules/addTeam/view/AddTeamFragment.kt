package net.pelozo.poketeams_challenge.modules.addTeam.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.facebook.share.internal.ShareConstants.IMAGE_URL
import dagger.hilt.android.AndroidEntryPoint
import net.pelozo.domain.Pokemon
import net.pelozo.domain.Team
import net.pelozo.poketeams_challenge.R
import net.pelozo.poketeams_challenge.base.BaseFragment
import net.pelozo.poketeams_challenge.databinding.FragmentAddTeamBinding
import net.pelozo.poketeams_challenge.modules.addTeam.constants.AddTeamConstants.Companion.MAX_POKEMONS_PER_TEAM
import net.pelozo.poketeams_challenge.modules.addTeam.constants.AddTeamConstants.Companion.MIN_POKEMONS_PER_TEAM
import net.pelozo.poketeams_challenge.modules.addTeam.constants.AddTeamConstants.Companion.glideOptions
import net.pelozo.poketeams_challenge.modules.addTeam.viewModel.AddTeamViewModel
import net.pelozo.poketeams_challenge.modules.selectPokemon.constants.SelectPokemonConstants
import net.pelozo.poketeams_challenge.observe
import net.pelozo.poketeams_challenge.observeEvent
import net.pelozo.poketeams_challenge.withViewModel


@AndroidEntryPoint
class AddTeamFragment : BaseFragment<FragmentAddTeamBinding>() {

    private val args: AddTeamFragmentArgs by navArgs()

    override fun initView() {
        super.initView()
        with(binding) {
            tvPokemons.text = getString(R.string.pokemons, "0")
            btAddPokemon.setOnClickListener {
                navigateToSelectPokemon()
            }
            btCreateTeam.setOnClickListener {
                if(validateInputs()){
                    getViewModel().addTeam(tietName.text.toString(), tietDescription.text.toString())
                    viewLoadingBackground.isVisible = true
                    pbLoading.isVisible = true
                    hideKeyboard()
                }

            }
        }
    }

    private fun validateInputs(): Boolean {
        with(binding) {
            if (tietName.text.toString().isEmpty()) {
                tilName.error = getString(R.string.error_team_name_empty)
                return false
            }
            if (binding.llPokemons.childCount < MIN_POKEMONS_PER_TEAM) {
                return false
            }
        }
        return true
    }

    override fun setUp(arguments: Bundle?) {
        super.setUp(arguments)
        getViewModel().setUp(args.region, args.team)
        setFragmentResultListener(
            SelectPokemonConstants.REQUEST_POKEMON
        ) { key, bundle ->
            getViewModel().fragmentResult(key, bundle)
        }
    }

    override fun getViewModel(): AddTeamViewModel = withViewModel {
        observe(getAddedPokemon()) { onGetAddedPokemon(it) }
        observe(getTeamLoaded()) { onGetTeamLoaded(it) }
        observeEvent(getAddedTeam()){onTeamAdded()}
    }

    private fun onTeamAdded() {
        findNavController().navigateUp()
    }

    private fun onGetAddedPokemon(pokemons: List<Pokemon>) {
        binding.llPokemons.removeAllViews()
        binding.tvPokemons.text = getString(R.string.pokemons, pokemons.size.toString())
        pokemons.forEach { pokemon ->
            val image = ImageView(binding.llPokemons.context)
            image.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            image.setOnClickListener {
                getViewModel().removePokemon(pokemon.name)
            }
            binding.llPokemons.addView(image)
            Glide
                .with(binding.llPokemons.context)
                .load(pokemon.imageUrl)
                .apply(glideOptions)
                .into(image)
        }
        binding.btAddPokemon.isVisible = pokemons.size < MAX_POKEMONS_PER_TEAM
    }


    private fun onGetTeamLoaded(team: Team) {
        with(binding){
            tvTitle.text = getString(R.string.edit_team)
            btCreateTeam.text = getString(R.string.edit_team)

            team.name?.let {
                tietName.setText(it)
            }
            team.description?.let {
                tietDescription.setText(it)
            }
        }
    }

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddTeamBinding = FragmentAddTeamBinding.inflate(inflater, container, false)

    private fun navigateToSelectPokemon() {
        findNavController().navigate(
            AddTeamFragmentDirections.actionAddTeamFragmentToSelectPokemonFragment(
                args.region
            )
        )
    }

}