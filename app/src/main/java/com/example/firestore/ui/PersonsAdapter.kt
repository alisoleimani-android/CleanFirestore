package com.example.firestore.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firestore.data.model.Person
import com.example.firestore.databinding.ItemPersonBinding

class PersonsAdapter : ListAdapter<Person, PersonsAdapter.PersonViewHolder>(DiffUtils()) {

    class DiffUtils : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.firstName == newItem.firstName
                    && oldItem.lastName == newItem.lastName
                    && oldItem.age == newItem.age
        }
    }

    class PersonViewHolder(
        private val mBinding: ItemPersonBinding
    ) : RecyclerView.ViewHolder(mBinding.root) {
        fun bind(person: Person) {
            mBinding.person = person
            mBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(ItemPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}