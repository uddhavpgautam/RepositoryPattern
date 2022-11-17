package com.example.repositorypattern.activities

import CardViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import com.example.repositorypattern.adapters.ScreenSlidePagerAdapter
import com.example.repositorypattern.animators.ViewPager2ViewHeightAnimator
import com.example.repositorypattern.toolbars.utils.CollapsingToolbar
import com.example.repositorypattern.transformers.ZoomOutPageTransformer

//eval `ssh-agent -s`; ssh-add /Users/roshanidahal/.ssh/id_rsa; git push

//never ever go with Inheritance BaseActivity approach. Things will get messy soon for BaseActivity
//when you have to add other logic and callbacks and you get even hard when you want to migrate
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewPagerForFragments: ViewPager2
    private lateinit var viewPagerForViews: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        viewPagerForFragments = findViewById(R.id.pager_for_fragments)
        viewPagerForFragments.setPageTransformer(ZoomOutPageTransformer())
        //all adapter used for viewpager2 are RecyclerView.Adapter
        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(this)
        viewPagerForFragments.adapter = screenSlidePagerAdapter

        viewPagerForViews = findViewById(R.id.pager_for_views)
        viewPagerForViews.setPageTransformer(ZoomOutPageTransformer())
        val cardViewAdapter = CardViewAdapter()
        viewPagerForViews.adapter = cardViewAdapter

        val viewPager2ViewHeightAnimator = ViewPager2ViewHeightAnimator()
        viewPager2ViewHeightAnimator.viewPager2 = viewPagerForFragments

        val collapsingToolbar = CollapsingToolbar(this)
        collapsingToolbar.setCollapsingToolbar()
    }

}
