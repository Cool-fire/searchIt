package com.asanam.dunzoassignment.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asanam.dunzoassignment.R
import com.asanam.dunzoassignment.service.model.Cse_image
import com.asanam.dunzoassignment.service.model.Items
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.synthetic.main.fragment_image_details.view.*
import kotlinx.android.synthetic.main.item_grid_image.view.*

fun newInstance() : ImageDetailsFragment = ImageDetailsFragment()

const val IMAGE_DETAILS = "image_details"

class ImageDetailsFragment : Fragment() {

    private lateinit var image: Items

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupImageDetails(view)
    }

    private fun setupImageDetails(view: View) {
        val cseImage = image.pagemap?.cse_image?.get(0)
        with(view) {
            cseImage?.let {
                setupImage(it, view)
                text_title.text = image.title
                snippet.text = image.snippet
                url.text = image.link
            }
        }
    }

    private fun setupImage(cse_image: Cse_image, view: View) {
        val requestOptions = RequestOptions()
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(this).load(cse_image.src).apply(requestOptions
            .fitCenter()
            .transform(RoundedCorners(20)))
            .transition(DrawableTransitionOptions.withCrossFade(factory)).into(view.image_detail_view)

    }

    fun addData(image: Items) {
        this.image  = image
    }
}