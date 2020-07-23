package com.example.calinews.presentation

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calinews.R
import com.example.calinews.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.supercaliman.domain.model.NewsArticle
import com.supercaliman.domain.model.NewsResponse
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.viewModel


class FirstFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var mAdapter: NewsAdapter
    private val TAG = FirstFragment::class.simpleName
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observer
        viewModel.getNewsUseCase().observe(viewLifecycleOwner, Observer { renderUi(it)})

        viewModel.verificationError.observe(viewLifecycleOwner, Observer {renderErrorUI(view,it)})

        floatingActionButton.setOnClickListener {viewModel.update()}
    }

    fun renderUi(response: List<NewsArticle>){
        listViewNews.layoutManager = viewModel.getLayoutManagerByOrientation(requireContext())
        mAdapter = NewsAdapter()
        listViewNews.adapter = mAdapter
        snackbar?.dismiss()
        mAdapter.data = response

    }

    fun renderErrorUI(view: View,errorMessage:String){
        snackbar = Snackbar.make(view,errorMessage,Snackbar.LENGTH_INDEFINITE)
                .setTextColor(Color.WHITE).setBackgroundTint(Color.RED)
        snackbar!!.show()

    }
}