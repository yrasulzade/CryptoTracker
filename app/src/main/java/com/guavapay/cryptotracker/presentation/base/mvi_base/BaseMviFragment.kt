package com.guavapay.cryptotracker.presentation.base.mvi_base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.guavapay.cryptotracker.presentation.base.BaseActivity

abstract class BaseMviFragment<INTENT : ViewIntent, STATE : ViewState, T : ViewDataBinding,
        VM : BaseViewModel<INTENT, STATE>> :
    Fragment(), IViewRenderer<STATE> {

    private lateinit var mActivity: BaseActivity<*, *>

    private lateinit var viewState: STATE
    val mState get() = viewState

    private var mViewModel: VM? = null

    lateinit var viewDataBinding: T
    private var mRootView: View? = null

    abstract fun getViewModel(): VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (mRootView == null) {
            viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
            mRootView = viewDataBinding.root
        }

        initUI()
        initEVENT()

        return mRootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = getViewModel()

        mViewModel?.state?.observe(this) {
            viewState = it
            render(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        mViewModel?.loadingState?.observe(
            viewLifecycleOwner
        ) { state -> if (state) showLoading() else hideLoading() }

    }

    val baseActivity: BaseActivity<*, *>
        get() = mActivity

    private fun hideKeyboard() {
        mActivity.hideKeyboard()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun showLoading() {
        mActivity.showLoading()
    }

    private fun hideLoading() {
        mActivity.hideLoading()
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int
    abstract fun initUI()
    abstract fun initEVENT()

    fun dispatchIntent(intent: INTENT) {
        mViewModel?.dispatchIntent(intent)
    }
}