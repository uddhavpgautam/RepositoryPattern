package com.example.repositorypattern.animators

import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class ViewPager2ViewHeightAnimator {

    //var property variable: Type = initialization: getters and setters
    var viewPager2: ViewPager2? = null
        get() {
            return field
        }
        set(value) {
            //if different than previous set value by backing field
            //initially field = null. field is implicit backing variable. Or you can use your own
            //backing property variable, but this is more manual work.

            //new viewpager came
            if (field != value) {
                //unregister pager change callback from old viewpager
                field?.unregisterOnPageChangeCallback(onPageChangeCallback)
                //back new viewpager to the backing field
                field = value
                //register page change callback to new just passed viewpager
                value?.registerOnPageChangeCallback(onPageChangeCallback)
            }
        }

    //viewpager2 always uses RecyclerView as it's first and only child
    //LinearLayoutManager extends RecyclerView.LayoutManager
    private val layoutManager: LinearLayoutManager? get() = (viewPager2?.getChildAt(0) as? RecyclerView)?.layoutManager as? LinearLayoutManager

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            recalculate(position, positionOffset)
        }
    }

    fun recalculate(position: Int, positionOffset: Float = 0f) = layoutManager?.apply {
        val leftView = findViewByPosition(position) ?: return@apply
        val rightView = findViewByPosition(position + 1)
        val setMeasure = {
            viewPager2?.apply {
                val leftHeight = getMeasuredViewHeightFor(leftView)
                layoutParams = layoutParams.apply {
                    height = if (rightView != null) {
                        val rightHeight = getMeasuredViewHeightFor(rightView)
                        leftHeight + ((rightHeight - leftHeight) * positionOffset).toInt()
                    } else {
                        leftHeight
                    }
                }
                invalidate()
            }
        }
        val onLayoutChanged =
            ViewTreeObserver.OnGlobalLayoutListener {
                setMeasure.invoke()
            }
        leftView.viewTreeObserver.addOnGlobalLayoutListener(onLayoutChanged)
        rightView?.viewTreeObserver?.addOnGlobalLayoutListener(onLayoutChanged)
        setMeasure.invoke()
    }

    private fun getMeasuredViewHeightFor(view: View): Int {
        val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
        val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(wMeasureSpec, hMeasureSpec)
        return view.measuredHeight
    }
}
