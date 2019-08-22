package com.asanam.dunzoassignment.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asanam.dunzoassignment.R
import com.asanam.dunzoassignment.service.model.Items
import com.asanam.dunzoassignment.view.adapter.ImageListAdapter.ImageViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_grid_image.view.*
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory


class ImageListAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ImageViewHolder>() {

    var mImagesList : ArrayList<Items> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = if(mImagesList.size > 0) {
        mImagesList.size
    } else {
        0
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(position)
    }

    fun addImages(newImagesList: List<Items>) {
        val startPosition = mImagesList.size
        mImagesList.addAll(newImagesList)
        notifyItemRangeChanged(startPosition, mImagesList.size)
    }

    fun clear() {
        mImagesList.clear()
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            with(itemView) {
                if (mImagesList.size > 0) {
                    val image = mImagesList[position]
                    try {
                        image.pagemap?.let {pagemap ->
                            image.title?.let{
                                caption_text.text = it
                            }
                            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                            val requestOptions = RequestOptions()
                            val cseThumbnail = pagemap.cse_thumbnail?.get(0)
                            cseThumbnail?.let {
                                Glide.with(this)
                                    .load(it.src)
                                    .apply(requestOptions
                                        .fitCenter()
                                        .transform(RoundedCorners(20))
                                        .error(R.drawable.abc_ic_star_black_48dp))
                                    .transition(withCrossFade(factory))
                                    .into(image_display)
                            }

                        }
                        itemView.setOnClickListener {
                                itemClickListener.onclick(image)
                        }
                    } catch (e : Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    interface ItemClickListener {
        fun onclick(image: Items)
    }
}