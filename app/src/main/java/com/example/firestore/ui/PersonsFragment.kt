package com.example.firestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firestore.databinding.FragmentPersonsBinding
import com.example.firestore.di.Injectable
import javax.inject.Inject

class PersonsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PersonsViewModel by viewModels { viewModelFactory }

    private val adapter = PersonsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FragmentPersonsBinding.inflate(inflater, container, false).also { binding ->
            binding.recyclerView.adapter = adapter

            binding.apply {
                btnAdd.setOnClickListener {
                    findNavController().navigate(PersonsFragmentDirections.actionRegisterDest())
                }
            }

            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        viewModel.apply {
            persons.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        }
    }

}