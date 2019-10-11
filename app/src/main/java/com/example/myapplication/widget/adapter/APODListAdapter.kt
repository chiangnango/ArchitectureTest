package com.example.myapplication.widget.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.myapplication.R
import com.example.myapplication.data.APOD
import com.example.myapplication.util.ImageUtil
import kotlinx.android.synthetic.main.widget_apod_item.view.*

class APODListAdapter(context: Context) : RecyclerView.Adapter<APODListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    var data: List<APOD> = emptyList()

    var clickListener: ((APOD) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.widget_apod_item, parent, false)).apply {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos == NO_POSITION) {
                    return@setOnClickListener
                }

                clickListener?.invoke(data[pos])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val apod = data[position]

        ImageUtil.load(apod.imageUrl, holder.thumbnail)
        holder.title.text = apod.title
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.thumbnail
        val title: TextView = itemView.title
    }
}