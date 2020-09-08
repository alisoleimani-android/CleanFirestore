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

            resultOfSnapshot.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.submitList(result.data)
                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

            filteredPersons.observe(viewLifecycleOwner, {
                when (it) {
                    is Result.Loading -> {
                        mBinding.progressBar.show()
                    }

                    is Result.Success -> {
                        if (viewModel.filterObservable) {
                            mBinding.progressBar.hide()

                            // Submitting list of items to RecyclerView
                            adapter.submitList(it.data)

                            viewModel.filterObservable = false
                        }
                    }

                    is Result.Error -> {
                        if (viewModel.filterObservable) {
                            mBinding.progressBar.hide()

                            // Showing errors
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                            viewModel.filterObservable = false
                        }
                    }
                }
            })

            resultOfDeletePerson.observe(viewLifecycleOwner, {
                when (it) {
                    is Result.Loading -> {
                        mBinding.progressBar.show()
                    }

                    is Result.Success -> {
                        if (viewModel.deleteObservable) {
                            mBinding.progressBar.hide()

                            Toast.makeText(
                                requireContext(),
                                "Successfully Deleted",
                                Toast.LENGTH_SHORT
                            ).show()

                            viewModel.deleteObservable = false
                        }
                    }

                    is Result.Error -> {
                        if (viewModel.deleteObservable) {
                            mBinding.progressBar.hide()

                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                            viewModel.deleteObservable = false
                        }
                    }
                }
            })

            resultOfIncreaseOrDecreaseAge.observe(viewLifecycleOwner, {
                when (it) {
                    is Result.Loading -> {
                        mBinding.progressBar.show()
                    }

                    is Result.Success -> {
                        if (viewModel.increaseOrDecreaseObservable) {
                            mBinding.progressBar.hide()

                            Toast.makeText(
                                requireContext(),
                                "Successfully Changed",
                                Toast.LENGTH_SHORT
                            ).show()

                            viewModel.increaseOrDecreaseObservable = false
                        }
                    }

                    is Result.Error -> {
                        if (viewModel.increaseOrDecreaseObservable) {
                            mBinding.progressBar.hide()

                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                            viewModel.increaseOrDecreaseObservable = false
                        }
                    }
                }
            })
        }
    }

}