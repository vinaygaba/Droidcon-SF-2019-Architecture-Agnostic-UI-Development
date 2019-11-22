package com.droidconsf.architectureagnosticuidevelopment.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.api.models.Comic
import kotlinx.android.synthetic.main.comicbook_item.view.comic_description
import kotlinx.android.synthetic.main.comicbook_item.view.comic_image
import kotlinx.android.synthetic.main.comicbook_item.view.comic_title

class ComicbooksAdapter : RecyclerView.Adapter<ComicbooksAdapter.ComicAdapterHolder>() {

    private val comics: MutableList<Comic> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicAdapterHolder =
        ComicAdapterHolder(LayoutInflater.from(parent.context).inflate(R.layout.comicbook_item, parent, false))


    override fun onBindViewHolder(holder: ComicAdapterHolder, position: Int) {
        val comic = comics[position]

        with(holder.itemView) {
            comic_title.text = comic.title
            comic_description.text = comic.description
            val secureImage = comic.thumbnail.imageUrl.replace("http", "https")
            Glide.with(holder.itemView.context)
                .load(secureImage)
                .centerCrop()
                .into(comic_image)
        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    fun updateData(comics: List<Comic>) {
        val currentOffsetStart = this.comics.size
        this.comics.addAll(comics)
        this.notifyItemRangeInserted(currentOffsetStart, this.comics.size)
    }

    class ComicAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}