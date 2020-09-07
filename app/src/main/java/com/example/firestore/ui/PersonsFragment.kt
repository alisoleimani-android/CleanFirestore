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
import javax.inject.Inject

class PersonsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PersonsViewModel by viewModels { viewModelFactory }

    private val adapter = PersonsAdapter()

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
        }
    }

}