package br.com.brunocarvalhs.profile.edit_profile

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.moneyReplace
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.usecase.auth.GetUserSessionUseCase
import br.com.brunocarvalhs.domain.usecase.auth.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserSessionUseCase: GetUserSessionUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel<EditProfileViewState>() {

    private var user: UserEntities? = getUserSessionUseCase.invoke().getOrNull()

    val name = ObservableField<String>(user?.name)

    val email = ObservableField<String>(user?.email)

    val salary = ObservableField(user?.formatSalary())

    fun update() {
        viewModelScope.launch {
            mutableState.value = EditProfileViewState.Loading
            updateUserUseCase.invoke(updateUser()).onSuccess {
                mutableState.value = EditProfileViewState.Success
            }.onFailure { error ->
                mutableState.value = EditProfileViewState.Error(error.message)
            }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = EditProfileViewState.Loading
            getUserSessionUseCase.invoke()
                .onSuccess { user = it }
                .onFailure { mutableState.value = EditProfileViewState.Error(it.message) }
        }
    }

    private fun updateUser(): UserEntities = (user as UserModel).copy(
        name = this.name.get(),
        salary = this.salary.get()?.moneyReplace()
    )
}