package com.guavapay.cryptotracker.presentation.base.mvi_base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : ViewIntent, STATE : ViewState> :
    ViewModel(),
    IModel<STATE, INTENT> {

    protected val mState = MutableLiveData<STATE>()
    override val state: LiveData<STATE>
        get() {
            return mState
        }

    final override fun dispatchIntent(intent: INTENT) {
        handleIntent(intent)
    }

    abstract fun handleIntent(intent: INTENT)

    private val loading = MutableLiveData<Boolean>()
    private val message = MutableLiveData<Int>()
    val loadingState: LiveData<Boolean>
        get() = loading

    fun loading(isLoading: Boolean) {
        loading.value = isLoading
    }
}