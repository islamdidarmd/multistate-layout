package com.islamdidarmd.multistatelayoutsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.islamdidarmd.multistatelayout.MultiStateLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLoading.setOnClickListener {
            multiStateLayout.setState(MultiStateLayout.State.LOADING)
        }

        btnContent.setOnClickListener {
            multiStateLayout.setState(MultiStateLayout.State.CONTENT)
        }

        btnEmpty.setOnClickListener {
            multiStateLayout.setState(MultiStateLayout.State.EMPTY)
        }
    }
}
