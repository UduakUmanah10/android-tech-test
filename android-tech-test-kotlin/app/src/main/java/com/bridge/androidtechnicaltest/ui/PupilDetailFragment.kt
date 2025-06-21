package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
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

        binding.back.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(PupilListFragment())

        }
    }
}