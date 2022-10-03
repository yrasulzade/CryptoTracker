package com.guavapay.cryptotracker.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.guavapay.cryptotracker.R
import com.guavapay.cryptotracker.data.database.models.CryptoRange
import com.guavapay.cryptotracker.databinding.DialogSetRangeBinding
import com.guavapay.cryptotracker.domain.useCase.LastSetRangeUseCase
import com.guavapay.cryptotracker.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DialogSetRange(
    private val type: String,
    private val cryptoRange: (CryptoRange) -> Unit,
    private val navigateHistory: (String) -> Unit
) :
    BaseDialogFragment() {

    @Inject
    lateinit var lastSetRangeUseCase: LastSetRangeUseCase
    private lateinit var binding: DialogSetRangeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSetRangeBinding.inflate(LayoutInflater.from(context))

        setDefaultValues()

        binding.saveValue.setOnClickListener {
            val minValue = binding.minValue.text.toString()
            val maxValue = binding.maxValue.text.toString()

            if (minValue.isEmpty() || maxValue.isEmpty()) {
                Toast.makeText(context, getString(R.string.empty_field_warning), Toast.LENGTH_LONG)
                    .show()
            } else {
                val range = CryptoRange(type, minValue.toDouble(), maxValue.toDouble())
                cryptoRange.invoke(range)

                dismiss()
            }
        }

        binding.navigateRateHistory.setOnClickListener {
            navigateHistory.invoke(type)
            dismiss()
        }

        return binding.root
    }

    private fun setDefaultValues() {
        lifecycleScope.launch(IO) {
            val crypto = lastSetRangeUseCase.execute(type)

            crypto?.minValue?.let {
                binding.minValue.setText(it.toString())
            }
            crypto?.maxValue?.let {
                binding.maxValue.setText(it.toString())
            }
        }
    }
}