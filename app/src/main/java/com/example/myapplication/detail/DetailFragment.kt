package com.example.myapplication.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.MainActivity
import com.example.myapplication.Navigator
import com.example.myapplication.R
import com.example.myapplication.architecture.InjectorUtil
import com.example.myapplication.data.APOD
import com.example.myapplication.main.MainViewModel
import com.example.myapplication.util.ImageUtil
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    companion object {
        private const val KEY_APOD_DATE = "apod_date"
        fun getInstance(apod: APOD): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_APOD_DATE, apod.date)
                }
            }
        }
    }

    private var selectedDate: String = ""
    private lateinit var viewModel: MainViewModel
    private lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            selectedDate = it.getString(KEY_APOD_DATE) ?: return
        }

        navigator = (context as MainActivity).navigator
        initViewModel()
        viewModel.fetchAPOD(selectedDate)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(activity!!, InjectorUtil.provideMainViewModelFactory(navigator))
                .get(MainViewModel::class.java)

        viewModel.apodList.observe(viewLifecycleOwner, Observer { list ->
            list.find { it.date == selectedDate }?.let {
                render(it)
            }
        })
    }

    private fun render(apod: APOD) {
        ImageUtil.load(apod.imageUrl, cover)
        title.text = apod.title
        explanation.text = apod.explanation
    }
}