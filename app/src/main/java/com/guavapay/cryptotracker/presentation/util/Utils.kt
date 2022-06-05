package com.guavapay.cryptotracker.presentation.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.guavapay.cryptotracker.R

object Utils {
    fun showLoadingDialog(context: Context?): Dialog {
        val progressDialog = Dialog(context!!)

        progressDialog.apply {
            show()
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // makes transparent background
            setContentView(R.layout.progress_dialog)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        return progressDialog
    }

    fun Double.roundFloatTwoDecimal(): Double {
        return "%.3f".format(this).toDouble()
    }
}



