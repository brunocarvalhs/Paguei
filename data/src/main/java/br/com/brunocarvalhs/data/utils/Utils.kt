package br.com.brunocarvalhs.data.utils

const val FORMAT_DATE = "dd/MM/yyyy"
const val PROMPT_FORMAT = "[00]{/}[00]{/}[0000]"
const val REGEX_TEXT = "[^\\d]"

fun String.moneyToDouble() =
    this.replace(REGEX_TEXT.toRegex(), "").toDouble()