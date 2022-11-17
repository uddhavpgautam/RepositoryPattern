package com.example.repositorypattern.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.repositorypattern.activities.CardFragment
import com.example.repositorypattern.activities.FragmentOne
import com.example.repositorypattern.cards.Card


class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return Card.DECK.size + 1 //1 for FragmentOne
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            in 1 until 53 -> CardFragment.create(Card.DECK[position - 1])
            else -> FragmentOne.newInstance()
        }
    }

}
