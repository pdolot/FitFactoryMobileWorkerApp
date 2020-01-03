package com.example.fitfactorymobileworkerapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.app.App
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener, MainInterface {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).setCurrentActivity(this)

        appBarLayout.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val scale = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        val bottomBarHeight = resources.getDimensionPixelSize(R.dimen.top_bar_height)

        if (abs(verticalOffset) in 0..bottomBarHeight){
            val s = abs(verticalOffset).toFloat() / bottomBarHeight.toFloat()
            topBar.fade(1.0f - s)
        }else{
            topBar.fade(0f)
        }
        topBar.changeBackgroundRadius(1.0f - scale)

    }

    override var actions: MainFragmentInterface?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun getAppBarLayout(): AppBarLayout? {
        return appBarLayout
    }
}
