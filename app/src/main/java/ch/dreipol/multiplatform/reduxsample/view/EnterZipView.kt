package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.ViewEnterZipBinding
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.utils.setNewText

class EnterZipView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private val possibleZipsAdapter = ArrayAdapter<String>(context, R.layout.view_dropdown_item, R.id.text, mutableListOf())
    private var textWatcher: TextWatcher? = null
    private val binding = ViewEnterZipBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var onZipEntered: (zip: Int?) -> Unit

    init {
        binding.zip.setAdapter(possibleZipsAdapter)
        binding.zip.setOnClickListener { binding.zip.showDropDown() }
        binding.zip.setDropDownBackgroundResource(R.drawable.round_dropdown_background)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeTextWatcher()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addTextWatcher()
    }

    fun init(onZipEntered: (zip: Int?) -> Unit) {
        this.onZipEntered = onZipEntered
    }

    fun update(zip: Int?, possibleZips: List<Int>, title: String) {
        binding.label.text = context?.getString(title)
        removeTextWatcher()
        binding.zip.setNewText(zip?.toString())
        addTextWatcher()
        possibleZipsAdapter.clear()
        possibleZipsAdapter.addAll(possibleZips.map { it.toString() })
        possibleZipsAdapter.notifyDataSetChanged()
    }

    private fun addTextWatcher() {
        textWatcher = binding.zip.addTextChangedListener(
            afterTextChanged = { text ->
                val newZip = text?.toString()?.toIntOrNull()
                onZipEntered.invoke(newZip)
            }
        )
    }

    private fun removeTextWatcher() {
        textWatcher?.let {
            binding.zip.removeTextChangedListener(textWatcher)
            textWatcher = null
        }
    }
}