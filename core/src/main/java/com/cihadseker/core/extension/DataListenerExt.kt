package com.cihadseker.core.extension

import androidx.annotation.MainThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.cihadseker.core.base.BaseFragment
import com.cihadseker.core.base.BaseViewModel

@MainThread
inline fun <reified VM : BaseViewModel> BaseFragment<*>.injectVM(brId: Int): Lazy<VM> {
    return viewModels<VM>().also {
        it.bindingListener(this, brId)
    }
}

inline fun <reified VM : BaseViewModel> Lazy<VM>.bindingListener(
    owner: BaseFragment<*>,
    brId: Int
) {
    with(owner) {
        lifecycleScope.launchWhenResumed {
            value.let { vm ->
                vi.lifecycleOwner = owner
                vi.setVariable(brId, vm)
                vi.executePendingBindings()
            }
        }
    }
}