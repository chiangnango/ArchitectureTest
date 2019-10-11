package com.example.myapplication.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.example.myapplication.MainActivity
import com.example.myapplication.Navigator
import com.example.myapplication.R
import com.example.myapplication.architecture.InjectorUtil
import com.example.myapplication.widget.adapter.APODListAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: APODListAdapter
    private lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigator = (context as MainActivity).navigator
        initViewModel()
        viewModel.fetchAPODList()

        initView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!, InjectorUtil.provideMainViewModelFactory(navigator))
            .get(MainViewModel::class.java)

        viewModel.apodList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        viewModel.showSpinner.observe(viewLifecycleOwner, Observer { visible ->
            spinner.visibility = if (visible) {
                VISIBLE
            } else {
                GONE
            }
        })
    }

    private fun initView() {
        horizontal_list.layoutManager = LinearLayoutManager(context).apply {
            orientation = HORIZONTAL
        }

        adapter = APODListAdapter(activity!!).apply {
            clickListener = {
                viewModel.onAPODItemClicked(it)
            }
        }
        horizontal_list.adapter = adapter
    }
}