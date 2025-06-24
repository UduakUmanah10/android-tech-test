package com.bridge.androidtechnicaltest.presentation.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentUpdateBinding
import com.bridge.androidtechnicaltest.presentation.viewmodel.UpdateStudent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val viewModel: UpdateStudent by activityViewModels()


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

        binding.enterCountry.doOnTextChanged { text, _, _, _ ->
            viewModel.enterCountry(text.toString())
            if (text.isNullOrBlank()) {
                binding.countryTextInputLayout.error = getString(R.string.Country_cannot_be_empty)
            } else {
                binding.countryTextInputLayout.error = null
            }
        }



        binding.enterName.doOnTextChanged { text, _, _, _ ->
            viewModel.enterName(text.toString())
            if (text.isNullOrBlank()) {
                binding.nameTextInputLayout.error =getString(R.string.name_cannot_be_empty)
            } else {
                binding.nameTextInputLayout.error = null
            }
        }

        binding.latitude.doOnTextChanged { text, _, _, _ ->
            val lat = text.toString().toDoubleOrNull()
            if (lat == null) {
                binding.latitudeTextInputLayout.error =getString(R.string.Latitude_must_be_a_valid_number)
            } else {
                binding.latitudeTextInputLayout.error = null
                viewModel.enterLatitude(lat)
            }
        }

        binding.longitude.doOnTextChanged { text, _, _, _ ->
            val lon = text.toString().toDoubleOrNull()
            if (lon == null) {
                binding.longitudeTextInputLayout.error = getString(R.string.Longitude_must_be_a_valid_number)
            } else {
                binding.longitudeTextInputLayout.error = null
                viewModel.enterLongitude(lon)
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteStudent()
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.updateStudent()
        }


    }
}