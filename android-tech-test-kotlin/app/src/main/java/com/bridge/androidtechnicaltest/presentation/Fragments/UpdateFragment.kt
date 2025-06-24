package com.bridge.androidtechnicaltest.presentation.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentUpdateBinding
import com.bridge.androidtechnicaltest.presentation.MainActivity
import com.bridge.androidtechnicaltest.presentation.viewmodel.UpdateStudentViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val viewModel: UpdateStudentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val country = arguments?.getString("country")
        val image = arguments?.getString("image")
        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")
        val pupilId = arguments?.getInt("pupilId")

        binding.enterName.setText(name)
        binding.enterCountry.setText(country)
        binding.latitude.setText(latitude?.toString())
        binding.longitude.setText(longitude?.toString())
        binding.imageUrlInput.setText(image)
        binding.pid.setText(pupilId?.toString())


        binding.enterCountry.doOnTextChanged { text, _, _, _ ->
            binding.countryTextInputLayout.error =
                if (text.isNullOrBlank()) getString(R.string.Country_cannot_be_empty) else null
        }

        binding.enterName.doOnTextChanged { text, _, _, _ ->
            binding.nameTextInputLayout.error =
                if (text.isNullOrBlank()) getString(R.string.name_cannot_be_empty) else null
        }

        binding.latitude.doOnTextChanged { text, _, _, _ ->
            val lat = text.toString().toDoubleOrNull()
            binding.latitudeTextInputLayout.error =
                if (lat == null) getString(R.string.Latitude_must_be_a_valid_number) else null
        }

        binding.longitude.doOnTextChanged { text, _, _, _ ->
            val lon = text.toString().toDoubleOrNull()
            binding.longitudeTextInputLayout.error =
                if (lon == null) getString(R.string.Longitude_must_be_a_valid_number) else null
        }


        binding.imageUrlInput.doOnTextChanged { text, _, _, _ ->
            binding.image.error =
                if (text.isNullOrBlank()) getString(R.string.Image_url_cannot_be_empty) else null
        }

        binding.pid.doOnTextChanged { text, _, _, _ ->
            val id = text.toString().toIntOrNull()
            binding.pupilId.error =
                if (id == null) getString(R.string.Pupil_id_must_be_a_valid_number) else null
        }




        binding.btnDelete.setOnClickListener {
            val updatedName = binding.enterName.text.toString()
            val updatedCountry = binding.enterCountry.text.toString()
            val updatedLatitude = binding.latitude.text.toString().toDoubleOrNull()
            val updatedLongitude = binding.longitude.text.toString().toDoubleOrNull()
            val updatedImage = binding.imageUrlInput.text.toString()
            val updatedPupilId = binding.pid.text.toString().toIntOrNull()
            viewModel.deleteStudent(
                    name = updatedName,
                    country = updatedCountry,
                    latitude = updatedLatitude,
                    longitude = updatedLongitude,
                    image = updatedImage,
                    pupilId = updatedPupilId

            )
            Toast.makeText(requireContext(), "Deleted: $updatedName" , Toast.LENGTH_SHORT ).show()
            (activity as? MainActivity)?.navigateToFragment(PupilListFragment())


        }


        binding.btnUpdate.setOnClickListener {
            val updatedName = binding.enterName.text.toString()
            val updatedCountry = binding.enterCountry.text.toString()
            val updatedLatitude = binding.latitude.text.toString().toDoubleOrNull()
            val updatedLongitude = binding.longitude.text.toString().toDoubleOrNull()
            val updatedImage = binding.imageUrlInput.text.toString()
            val updatedPupilId = binding.pid.text.toString().toIntOrNull()

            viewModel.updateStudentFromInput(
                name = updatedName,
                country = updatedCountry,
                latitude = updatedLatitude,
                longitude = updatedLongitude,
                image = updatedImage,
                pupilId = updatedPupilId
            )
            Toast.makeText(requireContext(), "Updated: $updatedName" , Toast.LENGTH_SHORT ).show()
            (activity as? MainActivity)?.navigateToFragment(PupilListFragment())
        }
    }
}
