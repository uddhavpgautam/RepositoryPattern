package com.example.repositorypattern.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.google.android.material.tabs.TabLayoutMediator

open class BaseCardFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(
            requireActivity().findViewById(R.id.into_tab_layout_for_fragments),
            requireActivity().findViewById(R.id.pager_for_fragments)
        )
        { _, _ -> }.attach()
    }

}
