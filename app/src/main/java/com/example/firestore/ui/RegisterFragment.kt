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
import androidx.navigation.fragment.navArgs
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

    val args  by navArgs<RegisterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false)

        mBinding.apply {

            btnSave.setOnClickListener {

                mBinding.root.clearFocus()

                if (args.person == null) {

                    viewModel.addNewPerson(
                        edFirstName.text.toString(),
                        edLastName.text.toString(),
                        edAge.text.toString()
                    )

                } else {

                    viewModel.updatePerson(
                        args.person!!,
                        edFirstName.text.toString(),
                        edLastName.text.toString(),
                        edAge.text.toString()
                    )

                }
            }

            person = args.person

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

            resultOfUpdatePerson.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        mBinding.progressBar.show()
                    }

                    is Result.Success -> {
                        Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
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