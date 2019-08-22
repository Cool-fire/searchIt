package com.asanam.dunzoassignment.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.asanam.dunzoassignment.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            val searchFragment = SearchFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container,searchFragment, SEARCH_FRAGMENT_TAG).commit()
        }
    }

    fun openSearchDetails(searchString: String) {
        val searchResultFragment = newInstance(searchString)
        supportFragmentManager.beginTransaction()
            .addToBackStack("searchresult")
            .replace(R.id.fragment_container, searchResultFragment, IMAGE_SEARCH_RESULT)
            .commit()
    }
}
