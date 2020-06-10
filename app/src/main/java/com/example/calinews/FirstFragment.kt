package com.example.calinews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calinews.R
import com.example.calinews.model.NewsArticle
import com.example.calinews.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.viewModel


class FirstFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var mAdapter:NewsAdapter
    private val TAG = FirstFragment::class.simpleName

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

        val mlayoutManager = LinearLayoutManager(view.context)
        listViewNews.layoutManager = mlayoutManager
        mAdapter = NewsAdapter()
        listViewNews.adapter = mAdapter
        
        //observer
        viewModel.getNewsRepository().observe(viewLifecycleOwner, Observer { mAdapter.data = it.articles!!})

        viewModel.verificationError.observe(viewLifecycleOwner, Observer { errorMessage ->
           Toast.makeText(context,errorMessage,Toast.LENGTH_LONG).show()
        })

        floatingActionButton.setOnClickListener { Log.d(TAG,"click") }
    }
}