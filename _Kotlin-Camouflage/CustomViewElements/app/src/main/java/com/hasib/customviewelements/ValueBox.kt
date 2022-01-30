package com.hasib.customviewelements

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.withStyledAttributes
import com.google.android.material.card.MaterialCardView
import com.hasib.customviewelements.databinding.LayoutValueBoxBinding

class ValueBox(context: Context?, attrs: AttributeSet?) : MaterialCardView(context, attrs) {
    private var binding: LayoutValueBoxBinding

    init {
        isClickable = true

        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = LayoutValueBoxBinding.inflate(inflater, this, true)

        context.withStyledAttributes(attrs, R.styleable.ValueBox) {
            binding.value.text = getString(R.styleable.ValueBox_boxValue)!!
            binding.label.apply {
                text = getString(R.styleable.ValueBox_boxLabel)!!
                backgroundTintList = getColorStateList(R.styleable.ValueBox_boxLabelShapeColor)
            }
            binding.executePendingBindings()
        }
    }

    fun setLabel(label: String) {
        binding.label.text = label
        invalidate()
        requestLayout()
    }

    fun setLabelShapeColor(color: ColorStateList) {
        binding.label.backgroundTintList = color
        invalidate()
        requestLayout()
    }

    fun setValue(value: String) {
        binding.value.text = value
        invalidate()
        requestLayout()
    }
}