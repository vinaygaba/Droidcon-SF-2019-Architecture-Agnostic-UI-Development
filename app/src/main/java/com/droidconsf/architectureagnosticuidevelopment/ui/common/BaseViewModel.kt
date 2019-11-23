package com.droidconsf.architectureagnosticuidevelopment.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.architecure.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun Disposable.autodispose() = compositeDisposable.add(this)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}