package com.cihadseker.main.ui

import android.os.Bundle
import com.cihadseker.core.base.BaseActivity
import com.cihadseker.main.R
import com.cihadseker.main.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?) {}

    override fun setListener() {}

    override fun setReceiver() {}
}