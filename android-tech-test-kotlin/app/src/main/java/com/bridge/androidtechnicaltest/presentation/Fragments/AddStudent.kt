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
        binding.createNew.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(PupilListFragment())
        }

        binding.countryTextField.editText?.doOnTextChanged { text, _, _, _ ->
            val country = text.toString().trim()
            if (country.isBlank()) {
                binding.countryTextField.error = getString(R.string.Country_cannot_be_empty)
            } else {
                binding.countryTextField.error = null
                viewMode.enterCountry(country)
            }
        }

        binding.EnterName.editText?.doOnTextChanged { text, _, _, _ ->
            val name = text.toString().trim()
            if (name.isBlank()) {
                binding.EnterName.error =getString(R.string.name_cannot_be_empty)
            } else {
                binding.EnterName.error = null
                viewMode.enterName(name)
            }
        }

        binding.EnterLatitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lat = text.toString().toDoubleOrNull()
            if (lat == null) {
                binding.EnterLatitude.error = getString(R.string.Latitude_must_be_a_valid_number)
            } else {
                binding.EnterLatitude.error = null
                viewMode.enterLatitude(lat)
            }
        }

        binding.EnterLongitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lon = text.toString().toDoubleOrNull()
            if (lon == null) {
                binding.EnterLongitude.error = getString(R.string.Longitude_must_be_a_valid_number)
            } else {
                binding.EnterLongitude.error = null
                viewMode.enterLongitude(lon)
            }
        }

        binding.EnterPupilId.editText?.doOnTextChanged { text, _, _, _ ->
            val pupilId = text.toString().toDoubleOrNull()
            if (pupilId == null) {
                binding.EnterPupilId.error = getString(R.string.pupil_id_must_be_valid_number)
            } else {
                binding.EnterPupilId.error = null
                viewMode.enterPupilId(pupilId)
            }
        }

        binding.ImageUrl.editText?.doOnTextChanged { text, _, _, _ ->
            val url = text.toString().trim()
            if (url.isEmpty() || !Patterns.WEB_URL.matcher(url).matches()) {
                binding.ImageUrl.error = getString(R.string.invalid_image_url)
            } else {
                binding.ImageUrl.error = null
                viewMode.enterImageUrl(url)
            }
        }

    }

}

