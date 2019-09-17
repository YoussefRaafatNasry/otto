package com.youssefraafatnasry.otto.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.youssefraafatnasry.otto.R

object CustomToast {

    fun showToast(context: Context, title: String, description: String) {

        val view = View.inflate(context, R.layout.toast, null)
        val titleTextView = view.findViewById<TextView>(R.id.toast_title_text_view)
        val descriptionTextView = view.findViewById<TextView>(R.id.toast_desc_text_view)

        titleTextView.text = title
        descriptionTextView.text = description

        val toast = Toast(context)
        toast.view = view
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 80)
        toast.show()

    }

}