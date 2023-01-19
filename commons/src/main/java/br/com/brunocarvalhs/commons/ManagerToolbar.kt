package br.com.brunocarvalhs.commons

import androidx.appcompat.widget.Toolbar

interface ManagerToolbar {
    val toolbar: Toolbar

    fun showToolbar()
    fun hideToolbar()
}
