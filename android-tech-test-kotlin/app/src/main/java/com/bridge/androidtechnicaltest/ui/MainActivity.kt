package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.ui.viewmodel.StudentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: StudentsViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fm = supportFragmentManager
            fm.beginTransaction()
                    .add(R.id.container, PupilListFragment())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reset) {
            TODO("Implement actions")
        }
        return super.onOptionsItemSelected(item)
    }
}