package com.tngdev.archcompapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tngdev.archcompapp.R
import com.tngdev.archcompapp.databinding.MainFragmentBinding
import com.tngdev.archcompapp.model.Pokemon
import com.tngdev.archcompapp.network.ApiResource

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = PokemonAdapter()
        binding.rvPokemons.adapter = adapter
        val observer = Observer<ApiResource<List<Pokemon>>> {
            when (it) {
                is ApiResource.Success -> {
                    showSuccess()
                    adapter.data = it.data
                    adapter.notifyDataSetChanged()
                }
                is ApiResource.Error -> {
                    showError(it.message ?: getText(R.string.unknown_error))

                }
                is ApiResource.NoInternet -> {
                    showNoInternet()
                }
                is ApiResource.Loading -> {
                    showLoading()
                }

            }
        }
        viewModel.getPokemons().observe(viewLifecycleOwner, observer)

    }

    private fun showSuccess() {
        binding.pgbPokemons.visibility = View.GONE
        binding.rvPokemons.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.pgbPokemons.visibility = View.VISIBLE
        binding.rvPokemons.visibility = View.INVISIBLE
    }

    private fun showError(message : CharSequence) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun showNoInternet() {
        Snackbar.make(binding.root, getText(R.string.no_internet), Snackbar.LENGTH_INDEFINITE).show()
    }


}