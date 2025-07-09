package com.bridge.androidtechnicaltest.presentation.Fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentAddStudentBinding
import com.bridge.androidtechnicaltest.presentation.viewmodel.AddStudentsViewModel


class AddStudent : Fragment() {

    private lateinit var binding: FragmentAddStudentBinding
    private val viewModel: AddStudentsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Real-time validation listeners
        binding.countryTextField.editText?.doOnTextChanged { text, _, _, _ ->
            val country = text.toString().trim()
            binding.countryTextField.error = when {
                country.length < 2 -> getString(R.string.error_country_too_short)
                country.length > 100 -> getString(R.string.error_country_too_long)
                else -> null
            }
        }

        binding.EnterName.editText?.doOnTextChanged { text, _, _, _ ->
            val name = text.toString().trim()
            binding.EnterName.error = when {
                name.length < 2 -> getString(R.string.error_name_too_short)
                name.length > 100 -> getString(R.string.error_name_too_long)
                else -> null
            }
        }

        binding.EnterLatitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lat = text.toString().toDoubleOrNull()
            binding.EnterLatitude.error = when {
                lat == null -> getString(R.string.error_latitude_invalid)
                lat !in -90.0..90.0 -> getString(R.string.error_latitude_range)
                else -> null
            }
        }

        binding.EnterLongitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lon = text.toString().toDoubleOrNull()
            binding.EnterLongitude.error = when {
                lon == null -> getString(R.string.error_longitude_invalid)
                lon !in -180.0..180.0 -> getString(R.string.error_longitude_range)
                else -> null
            }
        }

        binding.EnterPupilId.editText?.doOnTextChanged { text, _, _, _ ->
            val pupilId = text.toString().toIntOrNull()
            binding.EnterPupilId.error = if (pupilId == null)
                getString(R.string.error_pupil_id_invalid) else null
        }

        binding.ImageUrl.editText?.doOnTextChanged { text, _, _, _ ->
            val url = text.toString().trim()
            binding.ImageUrl.error = when {
                url.length < 11 -> getString(R.string.error_image_url_too_short)
                url.length > 1000 -> getString(R.string.error_image_url_too_long)
                !Patterns.WEB_URL.matcher(url).matches() -> getString(R.string.error_image_url_invalid)
                else -> null
            }
        }

        // Button click listener
        binding.createNew.setOnClickListener {
            val country = binding.countryTextField.editText?.text.toString().trim()
            val name = binding.EnterName.editText?.text.toString().trim()
            val latitude = binding.EnterLatitude.editText?.text.toString().toDoubleOrNull()
            val longitude = binding.EnterLongitude.editText?.text.toString().toDoubleOrNull()
            val pupilId = binding.EnterPupilId.editText?.text.toString().toIntOrNull()
            val imageUrl = binding.ImageUrl.editText?.text.toString().trim()

            var isValid = true

            // Validate one last time before submission
            if (country.length < 2 || country.length > 100) {
                binding.countryTextField.error = getString(R.string.error_country_too_short)
                isValid = false
            }

            if (name.length < 2 || name.length > 100) {
                binding.EnterName.error = getString(R.string.error_name_too_short)
                isValid = false
            }

            if (latitude == null || latitude !in -90.0..90.0) {
                binding.EnterLatitude.error = getString(R.string.error_latitude_invalid)
                isValid = false
            }

            if (longitude == null || longitude !in -180.0..180.0) {
                binding.EnterLongitude.error = getString(R.string.error_longitude_invalid)
                isValid = false
            }

            if (pupilId == null) {
                binding.EnterPupilId.error = getString(R.string.error_pupil_id_invalid)
                isValid = false
            }

            if (imageUrl.length < 11 || imageUrl.length > 1000 || !Patterns.WEB_URL.matcher(imageUrl).matches()) {
                binding.ImageUrl.error = getString(R.string.error_image_url_invalid)
                isValid = false
            }

            if (!isValid) {
                Toast.makeText(requireContext(), "Please fix the errors above", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // If all valid, proceed to submit
            viewModel.createStudent(
                country = country,
                name = name,
                latitude = latitude,
                longitude = longitude,
                pupilId = pupilId,
                imageUrl = imageUrl
            )

            Toast.makeText(requireContext(), "Student submitted", Toast.LENGTH_SHORT).show()
        }
    }
}