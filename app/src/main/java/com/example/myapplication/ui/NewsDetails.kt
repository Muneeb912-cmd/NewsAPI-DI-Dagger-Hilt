package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.repositories.Article
import com.example.myapplication.repositories.NewsResponse

class NewsDetails : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)
        val articleData = arguments?.getSerializable("articleData") as Article


        view.findViewById<TextView>(R.id.detailNewsTitle).text = articleData.title
        view.findViewById<TextView>(R.id.detailNewsAuthor).text = articleData.author
        view.findViewById<TextView>(R.id.detailNewsPublishedAt).text = articleData.publishedAt
        view.findViewById<TextView>(R.id.detailNewsDescription).text = articleData.description
        view.findViewById<TextView>(R.id.detailNewsContent).text = articleData.content
        Glide.with(view.findViewById<ImageView>(R.id.detailNewsImage).context)
            .load(articleData.urlToImage)
            .into(view.findViewById(R.id.detailNewsImage))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backButton = view.findViewById<ImageButton>(R.id.back)
        bacButtonPressed(backButton)

    }

    private fun bacButtonPressed(backButton: ImageButton) {
        backButton.setOnClickListener() {
            requireActivity().onBackPressed()
        }
    }
}