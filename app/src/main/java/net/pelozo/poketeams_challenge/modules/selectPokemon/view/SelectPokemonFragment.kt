package net.pelozo.poketeams_challenge.modules.selectPokemon.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import net.pelozo.domain.Pokemon
import net.pelozo.domain.Region
import net.pelozo.poketeams_challenge.base.BaseFragment
import net.pelozo.poketeams_challenge.databinding.FragmentSelectPokemonBinding
import net.pelozo.poketeams_challenge.modules.addTeam.view.AddTeamFragmentArgs
import net.pelozo.poketeams_challenge.modules.regionList.view.adapters.RegionAdapter
import net.pelozo.poketeams_challenge.modules.selectPokemon.constants.SelectPokemonConstants.Companion.KEY_POKEMON
import net.pelozo.poketeams_challenge.modules.selectPokemon.constants.SelectPokemonConstants.Companion.REQUEST_POKEMON
import net.pelozo.poketeams_challenge.modules.selectPokemon.view.adapter.PokemonAdapter
import net.pelozo.poketeams_challenge.modules.selectPokemon.viewModel.SelectPokemonViewModel
import net.pelozo.poketeams_challenge.observe
import net.pelozo.poketeams_challenge.withViewModel

@AndroidEntryPoint
class SelectPokemonFragment : BaseFragment<FragmentSelectPokemonBinding>() {

    private val args: SelectPokemonFragmentArgs by navArgs()

    private val pokemonAdapter = PokemonAdapter { pokemon ->
        setFragmentResult(REQUEST_POKEMON, bundleOf(KEY_POKEMON to pokemon))
        findNavController().navigateUp()
    }

    override fun initView() {
        super.initView()
        with(binding){
            rvPokemons.adapter = pokemonAdapter
        }
    }

    override fun setUp(arguments: Bundle?) {
        super.setUp(arguments)
        getViewModel().setUp(args.region)
    }

    override fun getViewModel(): SelectPokemonViewModel = withViewModel {
        observe(getPokemons()){onGetPokemons(it)}
    }

    private fun onGetPokemons(regions: List<Pokemon>) {
        pokemonAdapter.submitList(regions)
        binding.pbLoading.isVisible = false
    }

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSelectPokemonBinding =
        FragmentSelectPokemonBinding.inflate(inflater, container, false)


}