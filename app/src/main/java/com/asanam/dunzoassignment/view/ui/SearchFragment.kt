package com.asanam.dunzoassignment.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.asanam.dunzoassignment.R
import kotlinx.android.synthetic.main.fragment_search.view.*


 const val SEARCH_FRAGMENT_TAG = "search_fragment"
class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(view) {
            search_button.setOnClickListener {
                if(search_view.query.isNotEmpty()){
                    if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        val activity = activity as MainActivity
                        activity.openSearchDetails(search_view.query.toString())
                    }
                } else {
                    Toast.makeText(it.context, "Search String can't be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}