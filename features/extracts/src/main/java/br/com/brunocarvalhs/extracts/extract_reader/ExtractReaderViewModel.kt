package br.com.brunocarvalhs.extracts.extract_reader

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.extracts.R
import br.com.brunocarvalhs.paguei.features.costs.extract_reader.ExtractReaderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ExtractReaderViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ExtractReaderViewState>() {

    val cost = ExtractReaderFragmentArgs.fromSavedStateHandle(savedStateHandle).cost

    val name = ObservableField<String>(cost.name)

    val referenceMonth = ObservableField<String>(cost.dateReferenceMonth)

    val prompt = ObservableField<String>(cost.prompt)

    val value = ObservableField(context.getString(R.string.item_cost_value, cost.formatValue()))

    val datePayment = ObservableField(cost.datePayment)

    val barCode = ObservableField<String>(cost.barCode)

    val paymentVoucherUri = ObservableField<String>(cost.paymentVoucher)

    fun openFile(context: Context, url: String?) {
        try {
            mutableState.value = ExtractReaderViewState.Loading
            url?.let {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
            mutableState.value = ExtractReaderViewState.Success
        } catch (error: ActivityNotFoundException) {
            mutableState.value = ExtractReaderViewState.Error(error.message)
        }
    }
}