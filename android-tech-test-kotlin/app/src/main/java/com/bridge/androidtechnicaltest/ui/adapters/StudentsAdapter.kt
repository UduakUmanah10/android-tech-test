package com.bridge.androidtechnicaltest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.databinding.ViewHolderBinding
import com.bridge.androidtechnicaltest.domain.model.PupilItem

class StudentsAdapter : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(private val binding: ViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: PupilItem) {

            binding.apply {
                nameValue.text = student.name
                pupilId.text = student.pupilId.toString()
                latitudeValue.text = student.toString()
                longitudeValue.text = student.toString()
                countryValue.text = student.country
            }


        }
    }


    private val studentDiffUtil = object : DiffUtil.ItemCallback<PupilItem>() {
        override fun areItemsTheSame(oldItem: PupilItem, newItem: PupilItem): Boolean {
            return oldItem.pupilId == newItem.pupilId
        }

        override fun areContentsTheSame(oldItem: PupilItem, newItem: PupilItem): Boolean {
            return oldItem == newItem
        }

    }


    val studentListDiffer = AsyncListDiffer(this, studentDiffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(

            ViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false

            )
        )
    }

    override fun getItemCount(): Int {
        return studentListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentListDiffer.currentList[position]

        holder.bind(student)
        holder.itemView.setOnClickListener {
            itemClicked?.invoke(student)
        }
    }

    var itemClicked: ((PupilItem) -> Unit)? = null


}