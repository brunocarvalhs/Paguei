package br.com.brunocarvalhs.groups.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForEmailUseCase
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForIdUseCase
import br.com.brunocarvalhs.domain.usecase.group.UpdateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val sessionManager: SessionManager,
    private val getUserForEmailUseCase: GetUserForEmailUseCase,
    private val getUserForIdUseCase: GetUserForIdUseCase,
) : BaseViewModel<EditGroupViewState>() {

    val group: GroupEntities = EditGroupFragmentArgs.fromSavedStateHandle(savedStateHandle).group

    val name = ObservableField<String>(group.name)

    val member = ObservableField<String>()

    var members = mutableListOf<UserEntities>()
        private set

    fun fetchData() {
        viewModelScope.launch {
            group.members.map { memberId ->
                getUserForIdUseCase(memberId).onSuccess {
                    it?.let { userEntities -> members.add(userEntities) }
                    mutableState.value = EditGroupViewState.MemberSearchSuccess
                }.onFailure {
                    mutableState.value = EditGroupViewState.Error(it.message)
                }
            }
        }
    }

    fun registerMember(id: String? = null) {
        id?.let { registerMemberById(it) }
        member.get()?.let { email ->
            registerMemberByEmail(email)
        }
    }

    private fun registerMemberByEmail(email: String) = viewModelScope.launch {
        mutableState.value = EditGroupViewState.Loading
        getUserForEmailUseCase(email).onSuccess {
            it?.let { userEntities ->
                if (!members.contains(userEntities)) {
                    members.add(userEntities)
                } else {
                    mutableState.value = EditGroupViewState.Error("Usuário já vinculado")
                }
            }
            mutableState.value = EditGroupViewState.MemberSearchSuccess
        }.onFailure { error ->
            mutableState.value = EditGroupViewState.Error(error.message)
        }
    }

    private fun registerMemberById(id: String) = viewModelScope.launch {
        mutableState.value = EditGroupViewState.Loading
        getUserForIdUseCase(id).onSuccess {
            it?.let { userEntities ->
                if (!members.contains(userEntities)) {
                    members.add(userEntities)
                } else {
                    mutableState.value = EditGroupViewState.Error("Usuário já vinculado")
                }
            }
            mutableState.value = EditGroupViewState.MemberSearchSuccess
        }.onFailure { error ->
            mutableState.value = EditGroupViewState.Error(error.message)
        }
    }

    fun isIconCloseVisibility(member: UserEntities): Boolean =
        members.filter { it != sessionManager.getUser() }.contains(member)

    fun removeMember(member: UserEntities, callback: () -> Unit) {
        if (members.contains(member)) {
            members.remove(member)
            callback.invoke()
        }
    }

    fun update() {
        viewModelScope.launch {
            mutableState.value = EditGroupViewState.Loading
            updateGroupUseCase.invoke(updateGroup()).onSuccess {
                mutableState.value = EditGroupViewState.Success
            }.onFailure {
                mutableState.value = EditGroupViewState.Error(it.message)
            }
        }
    }

    private fun updateGroup(): GroupEntities = (group as GroupsModel).copy(
        name = this.name.get(),
        members = members.map { it.id }
    )
}