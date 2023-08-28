package br.com.brunocarvalhs.data.utils

import br.com.brunocarvalhs.domain.entities.CostEntities
import java.text.NumberFormat
import java.util.Locale

fun List<CostEntities>.sumOfFormatted(filter: ((CostEntities) -> Boolean)? = null): String {
    val numberFormat = NumberFormat.getInstance(Locale.getDefault())
    numberFormat.maximumFractionDigits = 2
    numberFormat.minimumFractionDigits = 2
    val result = filter?.let {
        this.filter(filter).mapNotNull { it.value }.sumOf { it.toDouble() }
    } ?: kotlin.run {
        this.mapNotNull { it.value }.sumOf { it.toDouble() }
    }
    return numberFormat.format(result)
}