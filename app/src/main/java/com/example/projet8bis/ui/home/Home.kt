package com.example.projet8bis.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet8bis.R
import com.example.projet8bis.adapter.ListAdapter
import com.example.projet8bis.databinding.FragmentHomeBinding
import com.example.projet8bis.model.Content
import com.example.projet8bis.model.SimpleResponse


import com.google.android.material.search.SearchBar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var listAdapter: ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchBar = binding.searchBar


        updateSearchBarDirection(searchBar)

        val loading = binding.loading



        listAdapter = ListAdapter(mutableListOf()) { content ->
            onItemClicked(content)
        }

        binding.recycler.adapter = listAdapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        collectNotes()

        binding.addButton.setOnClickListener { moveTo() }



        lifecycleScope.launch {

            viewModel.loading.collect {

                    isLoading ->
                loading.visibility = if (isLoading) View.VISIBLE else View.GONE


            }


        }

        viewModel.allUsers()

        binding.tablelayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.allUsers()
                    1 -> viewModel.allFavorites()
                    else -> viewModel.allUsers()
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })


        var searchString: String

        val searchViewBar = binding.searchViewBar

        searchViewBar.editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val text = searchViewBar.editText.text.toString()
                searchString = text
                viewModel.searchBarFunction(searchString)

                true

            } else {
                false
            }
        }


    }


    private fun moveTo() {
        findNavController().navigate(R.id.action_home_to_add)
    }

    private fun collectNotes() {
        lifecycleScope.launch {
            viewModel.homeAdd.collect { response ->
                when (response.status) {
                    is SimpleResponse.Status.Success -> {
                        listAdapter.updateList(response)


                    }

                    is SimpleResponse.Status.Failure -> {
                        lifecycleScope.launch {
                            viewModel.homeError.collect {


                            }
                        }

                    }

                    else -> {
                        lifecycleScope.launch {

                            viewModel.homeError.collect {


                            }
                        }
                    }
                }
            }
        }
    }

    private fun onItemClicked(content: Content) {
        val bundle = bundleOf(
            "id" to content.id,
            "name" to content.name,
            "firstname" to content.firstname,
            "phone" to content.phone,
            "email" to content.email,
            "birthday" to content.birthday,
            "wage" to content.wage,
            "note" to content.note,
            "favorite" to content.favorite,
            "picture" to content.picture,


            )



        findNavController().navigate(R.id.action_home_to_detail, bundle)
    }


    private fun updateSearchBarDirection(searchBar: SearchBar) {
        val currentLocale = resources.configuration.locales.get(0)
        if (currentLocale.language == "en") {

            searchBar.layoutDirection = View.LAYOUT_DIRECTION_LTR
            searchBar.textAlignment = View.TEXT_ALIGNMENT_VIEW_START


        } else {

            searchBar.layoutDirection = View.LAYOUT_DIRECTION_RTL
            searchBar.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}