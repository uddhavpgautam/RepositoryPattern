package com.example.repositorypattern.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import com.example.repositorypattern.cards.Card
import com.example.repositorypattern.cards.CardView
import com.google.android.material.tabs.TabLayoutMediator

class CardFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2
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
        TabLayoutMediator(requireActivity().findViewById(R.id.into_tab_layout), requireActivity().findViewById(R.id.pager))
        { _, _ ->}.attach()
        (requireActivity().findViewById(R.id.pager) as ViewPager2).apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                        view.post {
                            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                            view.measure(wMeasureSpec, hMeasureSpec)

                            if (this@apply.layoutParams.height != view.measuredHeight) {
                                this@apply.layoutParams = (this@apply.layoutParams as LinearLayout.LayoutParams)
                                    .also {
                                            lp -> lp.height = view.measuredHeight
                                    }
                            }
                        }
                }
            })
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun create(card: Card): CardFragment {
            val fragment = CardFragment()
            fragment.arguments = card.toBundle()
            return fragment
        }
    }
}