package com.example.firestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.firestore.R
import com.example.firestore.data.model.Filter
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog : DialogFragment() {

    private lateinit var mListener: OnClickListener

    companion object {
        @JvmStatic
        fun newInstance(listener: OnClickListener) = FilterDialog().apply {
            mListener = listener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFilter.setOnClickListener {
            mListener.onFilterClicked(
                Filter(
                    name = edFirstName.text.toString(),
                    fromAge = try {
                        edFromAge.text.toString().toInt()
                    } catch (e: Exception) {
                        -1
                    },
                    toAge = try {
                        edToAge.text.toString().toInt()
                    } catch (e: Exception) {
                        200
                    }
                )
            )

            dismiss()
        }
    }

    interface OnClickListener {
        fun onFilterClicked(filter: Filter)
    }

}