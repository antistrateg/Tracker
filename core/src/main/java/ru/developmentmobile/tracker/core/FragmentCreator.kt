package ru.developmentmobile.tracker.core

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

const val FRAGMENT_CREATOR_PARAM = "navigation.param"

open class FragmentCreator<T>(
    @IdRes val graphId: Int,
    @IdRes val nodeId: Int
) {
    @Suppress("UNCHECKED_CAST")
    val Fragment.param: T
        get() = arguments!!.get(FRAGMENT_CREATOR_PARAM) as T

    fun param(fragment: Fragment): T {
        @Suppress("UNCHECKED_CAST")
        return fragment.arguments!!.get(FRAGMENT_CREATOR_PARAM) as T
    }
}

fun <T : Parcelable> FragmentCreator<T>.navigate(fragment: Fragment, param: T) {
    fragment.findNavController().navigate(nodeId, Bundle().apply {
        putParcelable(FRAGMENT_CREATOR_PARAM, param)
    })
}
fun <T : Parcelable> FragmentCreator<T>.popBack(fragment: Fragment) {
    fragment.findNavController().popBackStack()
}

fun FragmentCreator<String>.navigate(fragment: Fragment, param: String? = null) {
    fragment.findNavController().navigate(nodeId, Bundle().apply {
        param?.let { putString(FRAGMENT_CREATOR_PARAM, it) }
    })
}

fun FragmentCreator<String>.navigate(fragment: Fragment, param: String, options: NavOptions) {
    fragment.findNavController().navigate(nodeId, Bundle().apply {
        putString(FRAGMENT_CREATOR_PARAM, param)
    }, options)
}




