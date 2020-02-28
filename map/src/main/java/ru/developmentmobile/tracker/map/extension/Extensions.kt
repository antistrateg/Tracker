package ru.developmentmobile.tracker.map.extension

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

fun Context.inflateSectionView(layoutId: Int, root: ViewGroup): View {


    val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater


    val sectionView = inflater.inflate(layoutId, root, false)

    root.removeAllViews()
    root.addView(sectionView)

    return sectionView
}