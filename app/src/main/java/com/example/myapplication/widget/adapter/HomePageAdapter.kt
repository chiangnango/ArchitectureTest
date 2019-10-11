package com.example.myapplication.widget.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.APOD

class HomePageAdapter(context: Context) :
    RecyclerView.Adapter<HomePageAdapter.ViewHolderWrapper>() {

    private val inflater = LayoutInflater.from(context)

    var data: List<List<APOD>> = emptyList()

    var clickListener: ((APOD) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWrapper {
        val recyclerView =
            inflater.inflate(R.layout.widget_horizontal_list, parent, false) as RecyclerView
        Log.d("nan", "onCreateViewHolder ${recyclerView.hashCode()}")
        return initView(recyclerView)
    }

    private fun initView(recyclerView: RecyclerView): ViewHolderWrapper {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        val adapter = APODListAdapter(recyclerView.context)
        adapter.clickListener = clickListener
        recyclerView.adapter = adapter

        return ViewHolderWrapper(recyclerView, adapter)
    }

    override fun onBindViewHolder(holder: ViewHolderWrapper, position: Int) {
        Log.d("nan", "onBindViewHolder $position")
        holder.adapter.data = data[position]
        holder.adapter.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolderWrapper(itemView: RecyclerView, val adapter: APODListAdapter) :
        RecyclerView.ViewHolder(itemView)
}