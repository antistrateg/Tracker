package ru.developmentmobile.tracker.map.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.presentation.router.MapRouter

class OtherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_other, container, false)

}