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
import com.example.firestore.data.model.Person
import com.example.firestore.data.model.response.Result
import com.example.firestore.databinding.FragmentRegisterBinding
import com.example.firestore.di.Injectable
import com.example.firestore.ui.utils.hide
import com.example.firestore.ui.utils.show
import javax.inject.Inject

class RegisterFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RegisterViewModel by viewModels { viewModelFactory }

    private lateinit var mBinding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false)

        mBinding.apply {

            btnSave.setOnClickListener {

                mBinding.root.clearFocus()

                viewModel.addPerson.postValue(
                    Person(
                        firstName = edFirstName.text.toString(),
                        lastName = edLastName.text.toString(),
                        age = edAge.text.toString().toInt()
                    )
                )
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
            resultOfAddPerson.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        mBinding.progressBar.show()
                    }

                    is Result.Success -> {
                        Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }

                    is Result.Error -> {
                        mBinding.progressBar.hide()
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

}