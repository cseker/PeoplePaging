package com.cihadseker.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    lateinit var vi: DB
    private var isViewCreated = false

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViews()
    abstract fun setListener()
    abstract fun setReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::vi.isInitialized) {
            vi = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            vi.lifecycleOwner = this
        }

        container?.removeView(vi.root)

        return vi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (!isViewCreated) {
            isViewCreated = true

            initViews()
            setListener()
        }
        setReceiver()
    }

    fun showToast(msg: String) {
        (activity as BaseActivity<*>).showToast(msg)
    }
}