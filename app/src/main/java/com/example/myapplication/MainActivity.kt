package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.ui.NewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var newsFragment: NewsFragment? = null
    private val FRAGMENT_TAG = "newsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the fragment's instance
        if (newsFragment != null && newsFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, FRAGMENT_TAG, newsFragment!!)
        }
    }
}
