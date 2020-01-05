package com.example.fitfactorymobileworkerapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.fitfactorymobileworkerapp.R
import com.example.fitfactorymobileworkerapp.app.App
import com.example.fitfactorymobileworkerapp.presentation.customViews.BottomBar
import com.example.fitfactorymobileworkerapp.presentation.customViews.TopBarExpanded
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener, MainInterface {

    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.deleteAll()
        (application as App).setCurrentActivity(this)

        appBarLayout.addOnOffsetChangedListener(this)

        if (viewModel.localStorage.isLogged()){
            findNavController(R.id.nav_host_fragment).navigate(R.id.toHomePage)
        }

        viewModel.localStorage.isLoggedLive().observe(this, Observer {
            if (it){
                viewModel.fetchLockerRoom()
                topBar.bindData(viewModel.localStorage.getUser())
            }else{
                viewModel.rxDisposer.clear()
                viewModel.deleteAll()
            }
        })

        viewModel.getLockerRoomKeys().observe(this, Observer {
            var men = it.filter { k -> k.type == "MALE" }
            var women = it.filter { k -> k.type == "FEMALE" }
            var menCurr = men.filter { k -> !k.free }.size
            var womenCurr = women.filter { k -> !k.free}.size
            bottomBar.bindData(menCurr, men.size, womenCurr, women.size)
        })

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val scale = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        val bottomBarHeight = resources.getDimensionPixelSize(R.dimen.top_bar_height)

        if (abs(verticalOffset) in 0..bottomBarHeight){
            val s = abs(verticalOffset).toFloat() / bottomBarHeight.toFloat()
            bottomBar.fade(1.0f - s)
        }else{
            bottomBar.fade(0f)
        }
        bottomBar.changeBackgroundRadius(1.0f - scale)
    }


    override var actions: MainFragmentInterface?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun getAppBarLayout(): AppBarLayout? {
        return appBarLayout
    }

    override fun getTopBar(): TopBarExpanded? {
        return topBar
    }

    override fun getContainer(): FrameLayout? {
        return container
    }

    override fun getBottomBar(): BottomBar? {
        return bottomBar
    }

    override fun getCoordinator(): CoordinatorLayout? {
        return coordinator
    }
}
