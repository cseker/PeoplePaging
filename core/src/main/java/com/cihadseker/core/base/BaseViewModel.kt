package com.cihadseker.core.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    var tag = this.javaClass.simpleName
}