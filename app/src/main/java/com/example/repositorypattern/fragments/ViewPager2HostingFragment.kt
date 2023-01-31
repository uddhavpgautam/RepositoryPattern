package com.example.repositorypattern.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.repositorypattern.R
import com.example.repositorypattern.adapters.ScreenSlidePagerAdapter
import com.example.repositorypattern.animators.ViewPager2ViewHeightAnimator
import com.example.repositorypattern.cards.adapters.CardViewAdapter
import com.example.repositorypattern.transformers.ZoomOutPageTransformer

class ViewPager2HostingFragment : Fragment() {

    private lateinit var viewPagerForFragments: ViewPager2
    private lateinit var viewPagerForViews: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager2_hosting, container, false)


        viewPagerForFragments = view.findViewById(R.id.pager_for_fragments)
        viewPagerForFragments.setPageTransformer(ZoomOutPageTransformer())
        //all adapter used for viewpager2 are RecyclerView.Adapter
        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPagerForFragments.adapter = screenSlidePagerAdapter

        viewPagerForViews = view.findViewById(R.id.pager_for_views)
        viewPagerForViews.setPageTransformer(ZoomOutPageTransformer())
        val cardViewAdapter = CardViewAdapter()
        viewPagerForViews.adapter = cardViewAdapter

        val viewPager2ViewHeightAnimator = ViewPager2ViewHeightAnimator()
        viewPager2ViewHeightAnimator.viewPager2 = viewPagerForFragments

        return view
    }

    companion object {
        fun newInstance() = ViewPager2HostingFragment()
    }

}
