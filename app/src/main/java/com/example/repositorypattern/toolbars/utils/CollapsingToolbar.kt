package com.example.repositorypattern.toolbars.utils

import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.newsapp.R
import com.example.repositorypattern.activities.MainActivity
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class CollapsingToolbar(mainActivity: MainActivity?) {

    lateinit var activity: AppCompatActivity

    init {
        mainActivity?.let {
            this.activity = mainActivity
        }
    }

    companion object {
        const val SWITCH_BOUND = 0.8f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F

    private lateinit var ivUserAvatar: ImageView
    private var EXPAND_AVATAR_SIZE: Float = 0F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var horizontalToolbarAvatarMargin: Float = 0F
    private lateinit var toolbar: Toolbar
    private lateinit var appBarLayout: AppBarLayout
    private var cashCollapseState: Pair<Int, Int>? = null
    private lateinit var titleToolbarText: AppCompatTextView
    private lateinit var titleToolbarTextSingle: AppCompatTextView
    private lateinit var invisibleTextViewWorkAround: AppCompatTextView
    private lateinit var background: FrameLayout

    fun setCollapsingToolbar() {
        this.activity.let {
            EXPAND_AVATAR_SIZE = it.resources.getDimension(R.dimen.default_expanded_image_size)
            COLLAPSE_IMAGE_SIZE = it.resources.getDimension(R.dimen.default_collapsed_image_size)
            horizontalToolbarAvatarMargin = it.resources.getDimension(R.dimen.activity_margin)
            appBarLayout = it.findViewById(R.id.app_bar_layout)
            toolbar = it.findViewById(R.id.anim_toolbar)
            ivUserAvatar = it.findViewById(R.id.imgb_avatar_wrap)
            titleToolbarText = it.findViewById(R.id.tv_profile_name)
            titleToolbarTextSingle = it.findViewById(R.id.tv_profile_name_single)
            background = it.findViewById(R.id.fl_background)
            invisibleTextViewWorkAround = it.findViewById(R.id.tv_workaround)

            (toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
            /**/
            appBarLayout.addOnOffsetChangedListener { appBarLayout, i ->
                if (isCalculated.not()) {
                    avatarAnimateStartPointY =
                        abs((appBarLayout.height - (EXPAND_AVATAR_SIZE + horizontalToolbarAvatarMargin)) / appBarLayout.totalScrollRange)
                    avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
                    verticalToolbarAvatarMargin = (toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
                    isCalculated = true
                }

                updateViews(abs(i / appBarLayout.totalScrollRange.toFloat()))
            }
        }
    }

    private fun updateViews(offset: Float) {
        when (offset) {
            in 0.15F..1F -> {
                titleToolbarText.apply {
                    if (visibility != View.VISIBLE) visibility = View.VISIBLE
                    alpha = (1 - offset) * 0.35F
                }
            }

            in 0F..0.15F -> {
                titleToolbarText.alpha = (1f)
                ivUserAvatar.alpha = 1f
            }
        }

        /** collapse - expand switch*/
        when {
            offset < SWITCH_BOUND -> Pair(TO_EXPANDED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            else -> Pair(TO_COLLAPSED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
        }.apply {
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED -> {
                            ivUserAvatar.translationX = 0F
                            this@CollapsingToolbar.activity.let {
                                background.setBackgroundColor(
                                    ContextCompat.getColor(
                                        it,
                                        R.color.color_transparent
                                    )
                                )
                            }

                            titleToolbarTextSingle.visibility = View.INVISIBLE
                        }
                        TO_COLLAPSED -> background.apply {
                            alpha = 0F
                            this@CollapsingToolbar.activity.let {
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        it,
                                        R.color.colorPrimary
                                    )
                                )
                            }
                            animate().setDuration(250).alpha(1.0F)

                            titleToolbarTextSingle.apply {
                                visibility = View.VISIBLE
                                alpha = 0F
                                animate().setDuration(500).alpha(1.0f)
                            }
                        }
                    }
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }

            /* Collapse avatar img*/
            ivUserAvatar.apply {
                when {
                    offset > avatarAnimateStartPointY -> {
                        val avatarCollapseAnimateOffset =
                            (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                        val avatarSize =
                            EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * avatarCollapseAnimateOffset
                        this.layoutParams.also {
                            it.height = Math.round(avatarSize)
                            it.width = Math.round(avatarSize)
                        }
                        invisibleTextViewWorkAround.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset)

                        this.translationX =
                            ((appBarLayout.width - horizontalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                        this.translationY =
                            ((toolbar.height - verticalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                    }
                    else -> this.layoutParams.also {
                        if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                            it.height = EXPAND_AVATAR_SIZE.toInt()
                            it.width = EXPAND_AVATAR_SIZE.toInt()
                            this.layoutParams = it
                        }
                        translationX = 0f
                    }
                }
            }
        }
    }

}