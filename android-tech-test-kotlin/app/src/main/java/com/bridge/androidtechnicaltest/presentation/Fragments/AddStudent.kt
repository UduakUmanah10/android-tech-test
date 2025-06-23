package com.bridge.androidtechnicaltest.presentation.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.bridge.androidtechnicaltest.databinding.FragmentAddStudentBinding
import com.bridge.androidtechnicaltest.presentation.MainActivity
import com.bridge.androidtechnicaltest.presentation.viewmodel.AddStudents

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddStudent : Fragment() {
    // TODO: Rename and change types of parameters

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
            viewMode.enterCountry(text.toString())
        }

        binding.EnterName.editText?.doOnTextChanged { text, _, _, _ ->
            viewMode.enterName(text.toString())
        }


        binding.EnterLatitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lat = text.toString().toDoubleOrNull()
            if (lat != null) {
                viewMode.enterLatitude(lat)
            }
        }


        binding.EnterLongitude.editText?.doOnTextChanged { text, _, _, _ ->
            val lon = text.toString().toDoubleOrNull()
            if (lon != null) {
                viewMode.enterLongitude(lon)
            }
        }

        binding

    }


}

