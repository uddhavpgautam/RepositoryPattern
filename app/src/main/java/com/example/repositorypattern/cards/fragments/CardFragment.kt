package com.example.repositorypattern.cards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.repositorypattern.cards.Card
import com.example.repositorypattern.cards.CardView
import com.google.android.material.tabs.TabLayoutMediator

class CardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cardView = CardView(layoutInflater, container)
        cardView.bind(Card.fromBundle(requireArguments()))
        return cardView.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(
            requireActivity().findViewById(R.id.into_tab_layout_for_fragments),
            requireActivity().findViewById(R.id.pager_for_fragments)
        )
        { _, _ -> }.attach()
    }

    companion object {

        fun create(card: Card): CardFragment {
            val fragment = CardFragment()
            fragment.arguments = card.toBundle()
            return fragment
        }
    }
}
