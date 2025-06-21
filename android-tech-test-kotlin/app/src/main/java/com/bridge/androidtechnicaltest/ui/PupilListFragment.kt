package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bridge.androidtechnicaltest.AddStudent
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentPupillistBinding
import com.bridge.androidtechnicaltest.domain.model.PupilItem
import com.bridge.androidtechnicaltest.ui.adapters.StudentsAdapter
import com.bridge.androidtechnicaltest.ui.viewmodel.StudentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
val pupilList = listOf(
    PupilItem(
        country = "Amsterdam",
        image = "http://lorempixel.com/640/480/sports?name=Abbigail Doyle",
        latitude = 0.0,
        longitude = 0.0,
        name = "Abbigail Doyle",
        pupilId = 7493,
        workType = "",
        time = 1750465100588,
        offlineDataOperation = 0
    ),
    PupilItem(
        country = "Kenya",
        image = "http://lorempixel.com/640/480/nature?name=Abbigail Yundt",
        latitude = -48.4534,
        longitude = 164.6,
        name = "Abbigail Yundt",
        pupilId = 7522,
        workType = "", // was cut off, assumed empty
        time = 0L, // time not captured, default added
        offlineDataOperation = 0
    )
)

@AndroidEntryPoint
class PupilListFragment : Fragment() {

    private lateinit var  binding: FragmentPupillistBinding
    //private lateinit var adapter:StudentsAdapter
    private lateinit var details: PupilDetailFragment

    val fragment = PupilDetailFragment()

    private val viewModel: StudentsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding= FragmentPupillistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)



        binding.fab.setOnClickListener{

            Toast.makeText(requireContext(), "fab clicked...", Toast.LENGTH_SHORT).show()
            (activity as? MainActivity)?.navigateToFragment(AddStudent())
        }

        binding.loadButton.setOnClickListener {

            Toast.makeText(requireContext(), "normal  clicked...", Toast.LENGTH_SHORT).show()
            (activity as? MainActivity)?.navigateToFragment(PupilDetailFragment())

        }



     //   setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    println("=======Collected state: $state============")

                    //adapter.studentListDiffer.submitList(pupilList)

                    if(state.data.isNotEmpty()){
                        binding.textView.text = state.data[0].name

                    }

                }
            }
        }
    }


//    private fun setUpRecyclerView(){
//        adapter = StudentsAdapter()
//        binding.rvPupilList.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter =  adapter
//        }
//    }
}