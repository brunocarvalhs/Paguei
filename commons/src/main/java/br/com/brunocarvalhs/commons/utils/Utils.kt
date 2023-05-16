package br.com.brunocarvalhs.commons.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.moneyToDouble() = this.replace(REGEX_TEXT.toRegex(), "").toDouble()

fun CharSequence.moneyReplace() = this.replace("[^0-9,]".toRegex(), "").replace(",", ".")

fun EditText.eventSetMonthTextField(calendar: Calendar, datePicker: MaterialDatePicker<Long>) {
    datePicker.addOnPositiveButtonClickListener {
        calendar.time = Date(it)
        calendar.add(Calendar.DATE, 1)
        val date = SimpleDateFormat(FORMAT_MONTH, Locale.getDefault()).format(calendar.time)
        this.setText(date)
    }
}

fun EditText.eventSetDateTextField(calendar: Calendar, datePicker: MaterialDatePicker<Long>) {
    datePicker.addOnPositiveButtonClickListener {
        calendar.time = Date(it)
        calendar.add(Calendar.DATE, 1)
        val date = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(calendar.time)
        this.setText(date)
    }
}

fun EditText.formattedDate() {
    val listener = MaskedTextChangedListener(PROMPT_FORMAT, this)
    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

fun EditText.formattedMonth() {
    val listener = MaskedTextChangedListener(PROMPT_MONTH_FORMAT, this)
    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

fun TextInputLayout.setupEditTextField(
    button: Button, @DrawableRes icon: Int? = null, event: () -> Unit = {}
) {
    this.setEndIconOnClickListener {
        if (this.editText?.isEnabled == false) {
            this.editText?.defineEnabled()
            icon?.let { this.setEndIconDrawable(it) } ?: run { this.isEndIconVisible = false }
            button.defineUpdateButton()
        } else event.invoke()
    }
}

private fun View.defineEnabled() {
    this.isEnabled = !this.isEnabled
}

private fun Button.defineUpdateButton() {
    this.visibility = View.VISIBLE
}

fun EditText.setupTextFieldValue() {
    this.let { valueEditText ->
        val currencyFormat = DecimalFormat.getCurrencyInstance()
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (s.isNotEmpty()) {
                        val parsed = s.toString().moneyToDouble() / 100
                        val formatted = currencyFormat.format(parsed)
                        valueEditText.removeTextChangedListener(this)
                        valueEditText.setText(formatted)
                        valueEditText.setSelection(formatted.length)
                        valueEditText.addTextChangedListener(this)
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        valueEditText.addTextChangedListener(textWatcher)
    }
}

fun Context.textCopyThenPost(textCopied: String, @StringRes messageSuccess: Int) {
    val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
    Toast.makeText(this, this.getString(messageSuccess), Toast.LENGTH_SHORT).show()
}

fun MaterialDatePicker<Long>.showDateAlert(fragment: Fragment) {
    this.show(fragment.parentFragmentManager, this.toString())
}

fun EditText.initDateConfig() {
    val listener = MaskedTextChangedListener(PROMPT_FORMAT, this)
    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

fun EditText.initMonthConfig() {
    val listener = MaskedTextChangedListener(PROMPT_MONTH_FORMAT, this)
    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

fun TextInputLayout.setupTextFieldDate(
    fragment: Fragment,
    datePicker: MaterialDatePicker<Long>,
    calendar: Calendar
) {
    this.editText?.initDateConfig()
    this.setEndIconOnClickListener { datePicker.showDateAlert(fragment) }
    this.editText?.eventSetDateTextField(calendar, datePicker)
}

fun TextInputLayout.setupTextFieldMonth(
    fragment: Fragment,
    datePicker: MaterialDatePicker<Long>,
    calendar: Calendar
) {
    this.editText?.initMonthConfig()
    this.setEndIconOnClickListener { datePicker.showDateAlert(fragment) }
    this.editText?.eventSetMonthTextField(calendar, datePicker)
}

fun TextInputLayout.setupEditTextFieldMonth(
    fragment: Fragment,
    button: Button,
    @DrawableRes icon: Int? = null,
    datePicker: MaterialDatePicker<Long>,
    calendar: Calendar
) {
    this.setupEditTextField(button, icon) { datePicker.showDateAlert(fragment) }
    this.editText?.formattedMonth()
    this.editText?.eventSetMonthTextField(calendar, datePicker)
}

fun TextInputLayout.setupEditTextFieldDate(
    fragment: Fragment,
    button: Button,
    @DrawableRes icon: Int? = null,
    datePicker: MaterialDatePicker<Long>,
    calendar: Calendar
) {
    this.setupEditTextField(button, icon) { datePicker.showDateAlert(fragment) }
    this.editText?.formattedDate()
    this.editText?.eventSetDateTextField(calendar, datePicker)
}

fun TextInputLayout.setupEditTextFieldValue(button: Button) {
    this.setupEditTextField(button)
    this.editText?.setupTextFieldValue()
}