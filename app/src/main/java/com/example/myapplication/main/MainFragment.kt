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
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.myapplication.MainActivity
import com.example.myapplication.Navigator
import com.example.myapplication.R
import com.example.myapplication.architecture.InjectorUtil
import com.example.myapplication.data.APOD
import com.example.myapplication.widget.adapter.HomePageAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: HomePageAdapter
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
        viewModel =
            ViewModelProviders.of(activity!!, InjectorUtil.provideMainViewModelFactory(navigator))
                .get(MainViewModel::class.java)

        viewModel.apodList.observe(viewLifecycleOwner, Observer {

            fun mockData(realData: List<APOD>): List<List<APOD>> {
                val result = ArrayList<List<APOD>>()
                repeat(20) {
                    val row = realData.subList(it, realData.size) + realData.subList(0, it)
                    result.add(row)
                }
                return result
            }

            adapter.data = mockData(it)
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
        homepage_view.layoutManager = LinearLayoutManager(context).apply {
            orientation = VERTICAL
        }

        adapter = HomePageAdapter(activity!!).apply {
            clickListener = {
                viewModel.onAPODItemClicked(it)
            }
        }
        homepage_view.adapter = adapter
    }
}