package com.bridge.androidtechnicaltest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.databinding.ViewHolderBinding
import com.bridge.androidtechnicaltest.domain.model.Pupils

class StudentsAdapter : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    var onDeleteClick: ((Pupils) -> Unit)? = null

    var itemClicked: ((Pupils) -> Unit)? = null


    inner class StudentViewHolder(private val binding: ViewHolderBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Pupils) {

            binding.latitudeValue.text = student.latitude.toString()
            binding.longitudeValue.text = student.longitude.toString()
            binding.nameValue.text = student.name
            binding.countryValue.text = student.country
            binding.pupilId.text = student.pupilId.toString()

            binding.root.setOnClickListener {
                itemClicked?.invoke(student)
            }

            binding.btnEdit.setOnClickListener {
                onDeleteClick?.invoke(student)
            }

        }
    }

    private val studentDiffUtil = object : DiffUtil.ItemCallback<Pupils>() {
        override fun areItemsTheSame(oldItem: Pupils, newItem: Pupils): Boolean {
            return oldItem.pupilId == newItem.pupilId
        }

        override fun areContentsTheSame(oldItem: Pupils, newItem: Pupils): Boolean {
            return oldItem == newItem
        }
    }

    val studentListDiffer = AsyncListDiffer(this, studentDiffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentListDiffer.currentList[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = studentListDiffer.currentList.size
}
