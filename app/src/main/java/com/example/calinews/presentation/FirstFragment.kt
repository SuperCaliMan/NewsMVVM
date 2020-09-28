package com.example.calinews.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.calinews.R
import com.example.calinews.SwipeHelper
import com.example.calinews.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.supercaliman.domain.model.NewsArticle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_first.*

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels()
    private var mAdapter = NewsAdapterPage()
    private var snackbar: Snackbar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listViewNews.layoutManager = viewModel.getLayoutManagerByOrientation(requireContext())

        listViewNews.adapter = mAdapter

        val a = SwipeHelper(
            onLeftSwipe = { Log.d("TAG","LEFT")},
            onRightSwipe = {Log.d("TAG","RIGHT")},
            swipeLeftBackgroundColor = resources.getColor(R.color.colorPrimaryDark),
            swipeLeftActionIconId = R.drawable.ic_baseline_add_24_white,
            swipeRightActionIconId = R.drawable.ic_baseline_add_box_24,
            swipeRightBackgroundColor = Color.RED,
            context = requireContext())
        val touchHelper = ItemTouchHelper(a);
        touchHelper.attachToRecyclerView(listViewNews)

        viewModel.newsPagedLiveData.observe(viewLifecycleOwner, Observer { renderUiPaged(it) })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {renderErrorUI(view,it)})

        swiperefresh.setOnRefreshListener {
           viewModel.refresh()
        }
    }


    fun renderUiPaged(response:PagedList<NewsArticle>){
        mAdapter.submitList(response)
        swiperefresh.isRefreshing = false
    }

    fun renderErrorUI(view: View,errorMessage:String){
        snackbar = Snackbar.make(view,errorMessage,Snackbar.LENGTH_INDEFINITE)
                .setTextColor(Color.WHITE).setBackgroundTint(Color.RED)
        snackbar!!.show()

    }
}