package com.tngdev.archcompapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tngdev.archcompapp.R
import com.tngdev.archcompapp.databinding.MainFragmentBinding
import com.tngdev.archcompapp.model.Pokemon

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
        val observer =
            Observer<List<Pokemon>> { list ->
                adapter.data = list
                adapter.notifyDataSetChanged()
            }
        viewModel.getPokemons().observe(viewLifecycleOwner, observer)

    }

}