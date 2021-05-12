package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.MainViewModel

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("name")
fun bindTextViewToName(textView: TextView, codename: String) {
    textView.text = codename
}

@BindingAdapter("closeApproachDate")
fun bindTextViewToCloseApproachDate(textView: TextView, closeApproachDate: String) {
    textView.text = closeApproachDate
}

@BindingAdapter("picOfTheDay")
fun bindImageViewToPicOfTheDay(img: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay != null && pictureOfDay.url.isNotBlank()) {
        Picasso.with(img.context)
            .load(pictureOfDay.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .fit()
            .centerCrop()
            .into(img)

    } else {
        img.setImageResource(R.drawable.error)
        img.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }
}

@BindingAdapter("progressStatus")
fun status(progressBar: ProgressBar, status : MainViewModel.STATUS?){
    when(status){
        MainViewModel.STATUS.LOADING ->{
            progressBar.visibility = View.VISIBLE
        }
        MainViewModel.STATUS.DONE -> {
            progressBar.visibility = View.INVISIBLE
        }
        MainViewModel.STATUS.ERROR -> {
            progressBar.visibility = View.INVISIBLE
        }
    }
}
@BindingAdapter("recyclerViewStatus")
fun recyclerViewStatus(recyclerView: RecyclerView, status: MainViewModel.STATUS?) {
    when (status) {
        MainViewModel.STATUS.LOADING -> {
            recyclerView.visibility = View.INVISIBLE
        }
        MainViewModel.STATUS.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
        MainViewModel.STATUS.ERROR -> {
            recyclerView.visibility = View.INVISIBLE
        }
    }
}
