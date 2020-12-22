package com.mut_jaeryo.saveaccount.category

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("app:categoryText")
fun setCategoryText(textView: TextView, categoryId: Int) {
    val filterType = Category.getFilter(categoryId)
    textView.text = textView.context.getString(filterType.typeId)
}

@BindingAdapter("app:categoryColor")
fun setCategoryColor(
    imageView: ImageView,
    categoryId: Int
) {
    val drawable: Drawable = imageView.drawable
    val color = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        imageView.context.getColor(Category.getColorWithFilterId(categoryId))
    } else {
        imageView.context.resources.getColor(Category.getColorWithFilterId(categoryId))
    }
    if (drawable is ShapeDrawable) {
        drawable.paint.color = color
    } else if (drawable is GradientDrawable) {
        drawable.setColor(color)
    }
}