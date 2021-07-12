package ru.developmentmobile.tracker.map.extension

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.inflateSectionView(layoutId: Int, root: ViewGroup): View {
    val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val sectionView = inflater.inflate(layoutId, root, false)
    root.removeAllViews()
    root.addView(sectionView)
    return sectionView
}


fun Bundle?.isNull(): Boolean = this == null