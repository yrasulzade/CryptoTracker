package com.guavapay.cryptotracker.presentation.base.mvi_base

interface IViewRenderer<STATE> {
    fun render(state: STATE)
}