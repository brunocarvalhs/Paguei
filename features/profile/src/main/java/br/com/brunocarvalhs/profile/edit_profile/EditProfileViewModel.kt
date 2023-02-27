package br.com.brunocarvalhs.profile.edit_profile

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: UserRepository
) : BaseViewModel<EditProfileViewState>() {

    private val user: UserEntities? = sessionManager.getUser()

    val name = ObservableField<String>(user?.name)

    val email = ObservableField<String>(user?.email)

    val salary = ObservableField<String>(user?.salary)

    fun update() {
        viewModelScope.launch {
            try {
                mutableState.value = EditProfileViewState.Loading
                val result = repository.update(updateUser())
                sessionManager.login(result, null)
                mutableState.value = EditProfileViewState.Success
            } catch (error: Exception) {
                mutableState.value = EditProfileViewState.Error(error.message)
            }
        }
    }

    private fun updateUser(): UserEntities = (user as UserModel).copy(
        name = this.name.get(),
        salary = this.salary.get()
    )
}