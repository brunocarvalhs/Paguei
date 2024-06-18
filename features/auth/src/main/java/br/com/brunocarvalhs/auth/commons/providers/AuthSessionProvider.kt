package br.com.brunocarvalhs.auth.commons.providers

interface AuthSessionProvider {
    fun get(): HashMap<String, Any>?
    fun set(user: HashMap<String, Any>): Unit
}