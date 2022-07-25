package com.example.converterapp.ui.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.converterapp.R
import com.squareup.picasso.Picasso


@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url: String) {
    Picasso.get().load(url).error(R.drawable.ic_icon_pirate_flag).into(this)
}