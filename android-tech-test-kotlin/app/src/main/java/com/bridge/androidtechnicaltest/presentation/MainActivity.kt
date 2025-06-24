package com.bridge.androidtechnicaltest.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.ActivityMainBinding
import com.bridge.androidtechnicaltest.presentation.Fragments.PupilListFragment
import com.bridge.androidtechnicaltest.presentation.viewmodel.AddStudentsViewModel
import com.bridge.androidtechnicaltest.presentation.viewmodel.UpdateStudentViewModel
import com.bridge.androidtechnicaltest.presentation.viewmodel.StudentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: StudentsViewModel by viewModels()
    private val addStudent: AddStudentsViewModel by viewModels()
    private val EditStudent: UpdateStudentViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToFragment(PupilListFragment())
       }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reset) {
            viewModel.refresh()
        }
        return super.onOptionsItemSelected(item)
    }

    fun navigateToFragment(fragment: Fragment, args: Bundle? = null) {
        args?.let {
            fragment.arguments = it
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Optional: adds the fragment to the back stack
            .commit()
    }
}