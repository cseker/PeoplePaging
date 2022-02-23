package com.cihadseker.core.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var vi: DB

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun setListener()
    abstract fun setReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vi = DataBindingUtil.setContentView(this, getLayoutId())
        vi.lifecycleOwner = this

        initViews(savedInstanceState)
        setListener()
        setReceiver()
    }

    fun showLoading() {
    }

    fun hideLoading() {
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}