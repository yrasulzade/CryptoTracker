package com.guavapay.cryptotracker.presentation.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar
import com.guavapay.cryptotracker.presentation.util.Utils.showLoadingDialog

abstract class BaseActivity<T : ViewDataBinding?, V : ViewModel> :
    AppCompatActivity() {
    private var mProgressDialog: Dialog? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    open fun getViewDataBinding(): T {
        return this.mViewDataBinding!!
    }

    open fun getViewModel2(): V {
        return this.mViewModel!!
    }

    open fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView<T>(this, getLayoutId())

        mViewModel = if (mViewModel == null) getViewModel() else mViewModel

        mViewDataBinding?.executePendingBindings()
    }

    open fun hideKeyboard() {
        Log.d(TAG, "hideKeyboard: ")
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    open fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    open fun showLoading() {
        hideLoading()
        mProgressDialog = showLoadingDialog(this)
    }

    open fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    open fun showMessage(message: String) {
        val parentLayout = findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(parentLayout,message, Snackbar.LENGTH_LONG)
        val snackTextView = snackBar.view.findViewById<View>(R.id.snackbar_text) as TextView
        snackTextView.maxLines = 10 // changes default snackBar max length, so that it can show EDV warnings fully
        snackBar.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
    }

    companion object {
        var TAG = "Base**"
    }

}