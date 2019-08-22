package com.asanam.dunzoassignment.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.asanam.dunzoassignment.R
import com.asanam.dunzoassignment.service.model.Items
import com.asanam.dunzoassignment.view.adapter.EndlessScrollListener
import com.asanam.dunzoassignment.view.adapter.ImageListAdapter
import com.asanam.dunzoassignment.viewmodel.SearchResultViewModel
import kotlinx.android.synthetic.main.fragment_image_results.*

fun newInstance(
    searchString: String
) : Fragment = ImageSearchResult().apply {
    arguments = Bundle(1).apply {
        putString(BUNDLE_SEARCH_STRING, searchString)
    }
}

const val BUNDLE_SEARCH_STRING = "search_string"
const val IMAGE_SEARCH_RESULT = "image_search_result"

class ImageSearchResult : Fragment(), ImageListAdapter.ItemClickListener {

    private lateinit var adapter: ImageListAdapter
    private lateinit var mviewModel: SearchResultViewModel
    private lateinit var searchString: String

    private lateinit var endlessScrollListener: EndlessScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.run {
            searchString = getString(BUNDLE_SEARCH_STRING, "")
        } ?: requireNotNull(arguments) {"No arguments supplied on instantiation"}
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        adapter = ImageListAdapter(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()

        endlessScrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView) {
                if(mviewModel.getNextPageOffset() != -1) {
                    showLoading()
                    mviewModel.fetchImages(searchString, mviewModel.getNextPageOffset() ?: -1)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mviewModel = ViewModelProviders.of(this).get(SearchResultViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        recycler_view.addOnScrollListener( endlessScrollListener)
        observeOnViewModel(mviewModel)
    }

    override fun onResume() {
        super.onResume()
        adapter.clear()
    }

    private fun observeOnViewModel(mviewModel: SearchResultViewModel) {
        adapter.clear()
        mviewModel.fetchImages(searchString, 1)
        mviewModel.imageResponseLiveData.observe(this, Observer { list ->
            list?.let {
                disableLoading()
                adapter.addImages(it)
                if(recycler_view.adapter == null) {
                    recycler_view.adapter = adapter
                }
            }
        })

        mviewModel.isError.observe(this, Observer { error ->
            if(error) {
                Toast.makeText(view?.context, "Maximum query limit reached", Toast.LENGTH_SHORT).show()
                if (recycler_view.layoutManager?.itemCount == 0) {
                    showErrorImage()
                }
                disableLoading()
            } else {
                showLoading()
                disableErrorImage()
            }
        })
    }

    fun showLoading() {
        progress_bar.isVisible = true
    }

    fun disableLoading() {
        progress_bar.isVisible = false
    }

    fun showErrorImage(){
        error_image.isVisible = true
    }

    fun disableErrorImage() {
        error_image.isVisible = false
    }


    override fun onclick(image: Items) {
        val detailsFragment = newInstance()
        detailsFragment.addData(image)

        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack("image_details")
            ?.replace(R.id.fragment_container, detailsFragment, IMAGE_DETAILS)?.commit()
    }
}
