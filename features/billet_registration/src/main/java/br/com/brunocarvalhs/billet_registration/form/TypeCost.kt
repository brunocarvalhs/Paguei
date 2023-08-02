package br.com.brunocarvalhs.billet_registration.form

import androidx.annotation.StringRes
import br.com.brunocarvalhs.billet_registration.R

enum class TypeCost(@StringRes val value: Int) {
    FIX(value = R.string.type_fix),
    VARIABLE(value = R.string.type_variable);

    companion object {
        fun ordinalOf(value: Int): TypeCost {
            return values()[value]
        }
    }
}