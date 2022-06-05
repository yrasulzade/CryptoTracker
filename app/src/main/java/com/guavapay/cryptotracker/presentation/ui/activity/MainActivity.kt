package com.guavapay.cryptotracker.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.guavapay.cryptotracker.presentation.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return viewModel
    }
}