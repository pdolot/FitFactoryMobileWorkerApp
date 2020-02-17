package com.example.fitfactorymobileworkerapp.presentation.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.data.models.app.User
import com.example.fitfactorymobileworkerapp.utils.SpanTextUtil
import com.example.fitfactorymobileworkerapp.utils.scaleValue
import kotlinx.android.synthetic.main.view_top_bar_expand.view.*
import kotlin.math.abs
import kotlin.math.roundToInt

class TopBarExpanded @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var maxViewSize = context.resources.getDimensionPixelSize(R.dimen.maxImageSize)
    private var minViewSize = context.resources.getDimensionPixelSize(R.dimen.minImageSize)
    private var viewSizeDiff = maxViewSize - minViewSize

    private val startMargin = context.resources.getDimensionPixelSize(R.dimen.profileImageStartMargin)
    private val endMargin = context.resources.getDimensionPixelSize(R.dimen.profileImageEndMargin)
    private val marginDiff = abs(startMargin - endMargin)

    init {
        View.inflate(context, R.layout.view_top_bar_expand, this)
    }

    fun scaleProfileImage(scale: Float){
        val margin = endMargin + (marginDiff * scale).roundToInt()
        val params = profileImage.layoutParams as MarginLayoutParams
        params.height = minViewSize + (viewSizeDiff * scale).roundToInt()
        params.width = minViewSize + (viewSizeDiff * scale).roundToInt()
        params.marginStart = margin
        params.topMargin = margin
        profileImage.layoutParams = params
    }

    fun fadeUserDetails(scale: Float){
        if (scale != 0f){
            userDetails.alpha = scale.scaleValue(0.6f, 1.0f, 0f, 1f)
        }else{
            userDetails.alpha = 0f
        }

    }

    fun bindData(user: User?) {
        user?.let {
            userFirstNameLastName.text = "${user.firstName} ${user.lastName}"
            userWorkPlace.text = user.workPlace?.name
            user.roles?.let{roles ->
                if (roles.contains("ROLE_WORKER")){
                    SpanTextUtil(context).setSpanOnTextView(
                        userRole, "konsultant", R.color.primaryLight
                    )
                }
                if (roles.contains("ROLE_COACH")){
                    SpanTextUtil(context).setSpanOnTextView(
                        userRole, "trener", R.color.primaryLight
                    )
                }
            }

            Glide.with(context)
                .load(it.profileImage)
                .placeholder(R.drawable.user_image)
                .into(profileImage)
        }
    }
}