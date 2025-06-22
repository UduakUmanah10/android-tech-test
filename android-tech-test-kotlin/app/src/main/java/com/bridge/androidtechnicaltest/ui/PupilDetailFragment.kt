package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentPupildetailBinding
import com.bridge.androidtechnicaltest.databinding.FragmentPupillistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PupilDetailFragment : Fragment() {

    private lateinit var binding: FragmentPupildetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding= FragmentPupildetailBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("PupilDetailFragment", "Arguments received: $arguments")

        val name = arguments?.getString("name")
        val country = arguments?.getString("country")
        val image = arguments?.getString("image")
        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")
        val pupilId = arguments?.getInt("pupilId")

        Log.d("PupilDetailFragment", "Name: $name, Country: $country, Latitude: $latitude")




        binding.back.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(PupilListFragment())

        }
        binding.latitudeValue.text  = latitude.toString()
        binding.longitudeValue.text =longitude.toString()
        binding.countryValue.text=country.toString()
        binding.pupilId.text =pupilId.toString()
        binding.nameValue.text= name


    }
}