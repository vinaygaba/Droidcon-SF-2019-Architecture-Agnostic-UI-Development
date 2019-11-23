package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic
import kotlinx.android.synthetic.main.comicbook_item.view.comic_description
import kotlinx.android.synthetic.main.comicbook_item.view.comic_image
import kotlinx.android.synthetic.main.comicbook_item.view.comic_title

class ComicbooksAdapter : RecyclerView.Adapter<ComicbooksAdapter.ComicAdapterHolder>() {

    private val comics: MutableList<Comic> = mutableListOf()

    var onComicbookClick: OnComicbookClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicAdapterHolder {
        val comicHolder = ComicAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comicbook_item, parent, false)
        )

        comicHolder.itemView.setOnClickListener {
            onComicbookClick?.invoke(comics[comicHolder.adapterPosition].id.toString())
        }

        return comicHolder
    }

    override fun onBindViewHolder(holder: ComicAdapterHolder, position: Int) {
        val comic = comics[position]

        with(holder.itemView) {
            comic_title.text = comic.title
            comic_description.text = comic.description
            val secureImage = comic.thumbnail.imageUrl
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

typealias OnComicbookClick = (String) -> Unit