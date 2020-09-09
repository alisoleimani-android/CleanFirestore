package com.example.firestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firestore.data.model.response.Result
import com.example.firestore.databinding.FragmentPersonsBinding
import com.example.firestore.di.Injectable
import com.example.firestore.ui.base.EventObserver
import com.example.firestore.ui.utils.hide
import com.example.firestore.ui.utils.show
import javax.inject.Inject

class PersonsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PersonsViewModel by viewModels { viewModelFactory }

    private val adapter = PersonsAdapter(
        onItemClicked = { person ->
            findNavController().navigate(PersonsFragmentDirections.actionRegisterDest(person))
        },

        onDeleteItemClicked = { person ->
            viewModel.deletePerson(person)
        },

        onIncreaseAge = { person ->
            viewModel.increaseOrDecreaseValueOfAge(person, 1)
        },

        onDecreaseAge = { person ->
            viewModel.increaseOrDecreaseValueOfAge(person, -1)
        }
    )

    private lateinit var mBinding: FragmentPersonsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPersonsBinding.inflate(inflater, container, false)

        mBinding.apply {
            recyclerView.adapter = adapter

            btnAdd.setOnClickListener {
                findNavController().navigate(PersonsFragmentDirections.actionRegisterDest())
            }

            btnFilter.setOnClickListener {
                FilterDialog.newInstance(object : FilterDialog.OnClickListener {
                    override fun onFilterClicked(filter: PersonsViewModel.Filter) {
                        viewModel.setFilter(filter)
                    }
                }).show(childFragmentManager, "FilterDialog")
            }
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        viewModel.apply {

            progress.observe(viewLifecycleOwner, EventObserver { showProgress ->
                if (showProgress) {
                    mBinding.progressBar.show()
                } else {
                    mBinding.progressBar.hide()
                }
            })

            error.observe(viewLifecycleOwner, EventObserver { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            })

            snapshot.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.submitList(result.data)
                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

            filteredPersons.observe(viewLifecycleOwner, EventObserver {
                // Submitting list of items to RecyclerView
                adapter.submitList(it)
            })

            onPersonDeleted.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(
                    requireContext(),
                    "Successfully Deleted",
                    Toast.LENGTH_SHORT
                ).show()
            })

            onAgeChanged.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(
                    requireContext(),
                    "Successfully Changed",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

}