package com.example.firestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.firestore.data.Person
import com.example.firestore.databinding.FragmentRegisterBinding
import com.example.firestore.di.Injectable
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
                viewModel.addPerson(
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

}