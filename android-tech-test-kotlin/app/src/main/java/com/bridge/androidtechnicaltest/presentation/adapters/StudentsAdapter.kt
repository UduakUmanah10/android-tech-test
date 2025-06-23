package com.bridge.androidtechnicaltest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.databinding.ViewHolderBinding
import com.bridge.androidtechnicaltest.data.local.PupilItemEntity

class StudentsAdapter : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    var onDeleteClick: ((PupilItemEntity) -> Unit)? = null

    var itemClicked: ((PupilItemEntity) -> Unit)? = null


    inner class StudentViewHolder(private val binding: ViewHolderBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: PupilItemEntity) {

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

    private val studentDiffUtil = object : DiffUtil.ItemCallback<PupilItemEntity>() {
        override fun areItemsTheSame(oldItem: PupilItemEntity, newItem: PupilItemEntity): Boolean {
            return oldItem.pupilId == newItem.pupilId
        }

        override fun areContentsTheSame(oldItem: PupilItemEntity, newItem: PupilItemEntity): Boolean {
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
