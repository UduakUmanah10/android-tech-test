package com.bridge.androidtechnicaltest.presentation.Fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentAddStudentBinding
import com.bridge.androidtechnicaltest.presentation.MainActivity
import com.bridge.androidtechnicaltest.presentation.viewmodel.AddStudents


class AddStudent : Fragment() {


    private lateinit var binding: FragmentAddStudentBinding

    private val viewMode: AddStudents by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryTextField.editText?.doOnTextChanged { text, _, _, _ ->
            val country = text.toString().trim()
            when {
                country.length < 2 -> binding.countryTextField.error = getString(R.string.error_country_too_short)
                country.length > 100 -> binding.countryTextField.error = getString(R.string.error_country_too_long)
                else -> binding.countryTextField.error = null
            }
        }

        binding.EnterName.editText?.doOnTextChanged { text, _, _, _ ->
            val name = text.toString().trim()
            when {
                name.length < 2 -> binding.EnterName.error = getString(R.string.error_name_too_short)
                name.length > 100 -> binding.EnterName.error = getString(R.string.error_name_too_long)
                else -> binding.EnterName.error = null
            }
        }

        binding.EnterLatitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lat = text.toString().toDoubleOrNull()
            when {
                lat == null -> binding.EnterLatitude.error = getString(R.string.error_latitude_invalid)
                lat !in -90.0..90.0 -> binding.EnterLatitude.error = getString(R.string.error_latitude_range)
                else -> binding.EnterLatitude.error = null
            }
        }

        binding.EnterLongitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lon = text.toString().toDoubleOrNull()
            when {
                lon == null -> binding.EnterLongitude.error = getString(R.string.error_longitude_invalid)
                lon !in -180.0..180.0 -> binding.EnterLongitude.error = getString(R.string.error_longitude_range)
                else -> binding.EnterLongitude.error = null
            }
        }

        binding.EnterPupilId.editText?.doOnTextChanged { text, _, _, _ ->
            val pupilId = text.toString().toIntOrNull()
            if (pupilId == null) {
                binding.EnterPupilId.error = getString(R.string.error_pupil_id_invalid)
            } else {
                binding.EnterPupilId.error = null
            }
        }

        binding.ImageUrl.editText?.doOnTextChanged { text, _, _, _ ->
            val url = text.toString().trim()
            when {
                url.length < 11 -> binding.ImageUrl.error = getString(R.string.error_image_url_too_short)
                url.length > 1000 -> binding.ImageUrl.error = getString(R.string.error_image_url_too_long)
                !Patterns.WEB_URL.matcher(url).matches() -> binding.ImageUrl.error = getString(R.string.error_image_url_invalid)
                else -> binding.ImageUrl.error = null
            }
        }

        val country = binding.countryTextField.editText?.text.toString().trim()
        val name = binding.EnterName.editText?.text.toString().trim()
        val latitude = binding.EnterLatitude.editText?.text.toString().toDoubleOrNull()
        val longitude = binding.EnterLongitude.editText?.text.toString().toDoubleOrNull()
        val pupilId = binding.EnterPupilId.editText?.text.toString().toIntOrNull()
        val imageUrl = binding.ImageUrl.editText?.text.toString().trim()

        binding.createNew.setOnClickListener {

            viewMode.createStudent(
                country = country,
                name = name,
                latitude = latitude,
                longitude = longitude,
                pupilId = pupilId,
                imageUrl = imageUrl
            )
        }

    }


}

