package com.islamdidarmd.multistatelayout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout


/**
 * This class is a compound layout that shows exact view based on the passed state
 *
 * Use this class in your xml descriptor and edit attrs
 *
 * @param context The Context
 * @param attrs the AttributeSet
 * @param defStyleAttr the default Style
 */
open class MultiStateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    val TAG = this.javaClass.name

    var emptyLayout: View? = null
    var loadingLayout: View? = null
    var contentLayout: View? = null

    var netWorkStatusLayout: View? = null
    var networkStatusEnabled = false

    private val array = context.obtainStyledAttributes(attrs, R.styleable.msl, defStyleAttr, 0)
    private var networkStateReceiver: BroadcastReceiver? = null

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (networkStateReceiver != null) {
            context.unregisterReceiver(networkStateReceiver)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (array != null) {
            val emptyId = array.getResourceId(R.styleable.msl_emptyLayout, 0)
            val loadingId = array.getResourceId(R.styleable.msl_loadingLayout, 0)
            val contentId = array.getResourceId(R.styleable.msl_contentLayout, 0)
            this.networkStatusEnabled = array.getBoolean(R.styleable.msl_showConnectionStatus, false)

            this.emptyLayout = findViewById(emptyId)
            this.loadingLayout = findViewById(loadingId)
            this.contentLayout = findViewById(contentId)

            if (this.emptyLayout == null) {
                this.emptyLayout = LayoutInflater.from(context).inflate(R.layout.layout_no_data, this, false)
            }
            if (this.loadingLayout == null) {
                this.loadingLayout = LayoutInflater.from(context).inflate(R.layout.layout_loading, this, false)
            }

            initLayouts()

            setState(State.CONTENT)
            initNetworkReceiver()

            array.recycle()
        }
    }

    fun initLayouts() {
        orientation = VERTICAL
        this.removeAllViews()

        netWorkStatusLayout = LayoutInflater.from(context).inflate(R.layout.layout_connectivity, this, false)

        addView(netWorkStatusLayout)
        addView(contentLayout)
        addView(emptyLayout)
        addView(loadingLayout)

    }

    fun isOnline(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //should check null because in airplane mode it will be null
        return netInfo != null && netInfo.isConnected
    }

    private fun initNetworkReceiver() {
        networkStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {

                if (isOnline(context)) {
                    Log.d(TAG, "isOnline")
                    hide(netWorkStatusLayout)
                } else if (networkStatusEnabled) {
                    Log.d(TAG, "not online")
                    show(netWorkStatusLayout)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(networkStateReceiver, filter)
    }

    /** shows exact view based on the passed state
     * @param state the current state that shows exact view
     */
    fun setState(state: State) {
        when (state) {
            State.LOADING -> {
                hide(emptyLayout)
                hide(contentLayout)
                show(loadingLayout)
            }

            State.EMPTY -> {
                hide(loadingLayout)
                hide(contentLayout)
                show(emptyLayout)
            }

            State.CONTENT -> {
                hide(loadingLayout)
                hide(emptyLayout)
                show(contentLayout)
            }
        }
    }

    enum class State {
        LOADING,
        EMPTY,
        CONTENT
    }
}

/** Hides a view
 * @param view is the view
 */
fun hide(view: View?) {
    if (view != null) {
        view.visibility = View.GONE
    }
}

/** Shows a view
 * @param view is the view
 */
fun show(view: View?) {
    if (view != null) {
        view.visibility = View.VISIBLE
    }
}

