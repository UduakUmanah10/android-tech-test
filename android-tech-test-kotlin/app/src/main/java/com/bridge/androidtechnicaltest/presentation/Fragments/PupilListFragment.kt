package com.bridge.androidtechnicaltest.presentation.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bridge.androidtechnicaltest.databinding.FragmentPupillistBinding
import com.bridge.androidtechnicaltest.presentation.MainActivity
import com.bridge.androidtechnicaltest.presentation.adapters.StudentsAdapter
import com.bridge.androidtechnicaltest.presentation.viewmodel.StudentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val SEARCH_DELAY_TIME = 30L

@AndroidEntryPoint
class PupilListFragment : Fragment() {

    private lateinit var binding: FragmentPupillistBinding
    private lateinit var studentAdapter: StudentsAdapter

    val fragment = PupilDetailFragment()

    private val viewModel: StudentsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPupillistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        var searchJob: Job? = null

        binding.searchPupil.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(SEARCH_DELAY_TIME)
                val query = editable.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchForPupil(query)
                }
            }
        }


        binding.fab.setOnClickListener {

            (activity as? MainActivity)?.navigateToFragment(AddStudent())
        }

        binding.search.setOnClickListener {

           val input = binding.searchPupil.text.toString()
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(SEARCH_DELAY_TIME)
                val query = input
                if (query.isNotEmpty()) {
                    viewModel.searchForPupil(query)
                }
            }
        }

        studentAdapter.onDeleteClick = { pupil ->

            val bundle = Bundle().apply {
                putString("name", pupil.name)
                putString("country", pupil.country)
                putString("image", pupil.image)
                pupil.latitude?.let { putDouble("latitude", it) }
                pupil.longitude?.let { putDouble("longitude", it) }
                pupil.pupilId?.let { putInt("pupilId", it) }
            }


            (activity as? MainActivity)?.navigateToFragment(UpdateFragment(), bundle)

        }


        studentAdapter.itemClicked = { pupil ->
            println("== $pupil ==")

            val bundle = Bundle().apply {
                putString("name", pupil.name)
                putString("country", pupil.country)
                putString("image", pupil.image)
                pupil.latitude?.let { putDouble("latitude", it) }
                pupil.longitude?.let { putDouble("longitude", it) }
                pupil.pupilId?.let { putInt("pupilId", it) }


            }

            (activity as? MainActivity)?.navigateToFragment(PupilDetailFragment(), bundle)

        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState1.collectLatest { state ->

                    println(" /======= new ui Collected state: ${state.data}  ${state.error} ============ /")
                    studentAdapter.studentListDiffer.submitList(state.data)

                    if (state.loading) {
                        binding.progressBaree.visibility = View.VISIBLE
                    } else {
                        binding.progressBaree.visibility = View.GONE
                    }

                }

            }
        }

    }


    private fun setUpRecyclerView() {
        studentAdapter = StudentsAdapter()
        binding.rvStudentList.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(activity)

        }


    }

}