package com.tngdev.archcompapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tngdev.archcompapp.R
import com.tngdev.archcompapp.databinding.MainFragmentBinding
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.ApiResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter : PokemonAdapter?= null

    private lateinit var viewModel: PokemonListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)

        adapter = PokemonAdapter()
        binding.rvPokemons.adapter = adapter
        val observer = Observer<ApiResource<LiveData<List<Pokemon>>>> {
            when (it) {
                is ApiResource.Success -> {
                    showData(it.data)
                    hideLoading()
                }
                is ApiResource.Error -> {
                    showData(it.data)
                    hideLoading()
                    showError(it.message ?: getText(R.string.unknown_error))
                }
                is ApiResource.NoInternet -> {
                    showData(it.data)
                    hideLoading()
                    showNoInternet()
                }
                is ApiResource.Loading -> {
                    showLoading()
                    showData(it.data)
                }

            }
        }
        viewModel.getListPokemon().observe(viewLifecycleOwner, observer)

        binding.srlPokemons.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun showData(data : LiveData<List<Pokemon>>?) {
        data?. let { liveData ->
            liveData.observe(viewLifecycleOwner,
                Observer<List<Pokemon>> {
                    adapter?.data = it
                    adapter?.notifyDataSetChanged()
                })
        }
    }

    private fun showLoading() {
        binding.srlPokemons.isRefreshing = true
    }

    private fun hideLoading() {
        binding.srlPokemons.isRefreshing = false
    }

    private fun showError(message : CharSequence) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun showNoInternet() {
        Snackbar.make(binding.root, getText(R.string.no_internet), Snackbar.LENGTH_INDEFINITE).show()
    }


}