package br.com.brunocarvalhs.groups.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.services.NotificationService
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForEmailUseCase
import br.com.brunocarvalhs.domain.usecase.auth.GetUserForIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject constructor(
    private val repository: GroupsRepository,
    private val sessionManager: SessionManager,
    private val getUserForEmailUseCase: GetUserForEmailUseCase,
    private val getUserForIdUseCase: GetUserForIdUseCase,
    private val notificationService: NotificationService
) : BaseViewModel<GroupRegisterViewState>() {

    val name = ObservableField<String>()

    val member = ObservableField<String>()

    var members = mutableListOf<UserEntities>()
        private set

    init {
        sessionManager.getUser()?.let { members.add(it) }
    }

    fun registerMember(id: String? = null) {
        id?.let { registerMemberById(it) }
        member.get()?.let { email ->
            registerMemberByEmail(email)
        }
    }

    private fun registerMemberByEmail(email: String) = viewModelScope.launch {
        mutableState.value = GroupRegisterViewState.Loading
        getUserForEmailUseCase(email).onSuccess {
            it?.let { userEntities ->
                if (!members.contains(userEntities)) {
                    members.add(userEntities)
                } else {
                    mutableState.value = GroupRegisterViewState.Error("Usuário já vinculado")
                }
            }
            mutableState.value = GroupRegisterViewState.MemberSearchSuccess
        }.onFailure { error ->
            mutableState.value = GroupRegisterViewState.Error(error.message)
        }
    }

    private fun registerMemberById(id: String) = viewModelScope.launch {
        mutableState.value = GroupRegisterViewState.Loading
        getUserForIdUseCase(id).onSuccess {
            it?.let { userEntities ->
                if (!members.contains(userEntities)) {
                    members.add(userEntities)
                } else {
                    mutableState.value = GroupRegisterViewState.Error("Usuário já vinculado")
                }
            }
            mutableState.value = GroupRegisterViewState.MemberSearchSuccess
        }.onFailure { error ->
            mutableState.value = GroupRegisterViewState.Error(error.message)
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

    fun save() {
        viewModelScope.launch {
            try {
                val group = generateGroups()
                mutableState.value = GroupRegisterViewState.Loading
                repository.add(group)
                sessionManager.sessionGroup(group)
                notificationMembers(group)
                mutableState.value = GroupRegisterViewState.Success
            } catch (error: Exception) {
                mutableState.value = GroupRegisterViewState.Error(error.message)
            }
        }
    }

    private fun generateGroups(): GroupEntities {
        val members = members.map { it.id }
        return GroupsModel(
            name = name.get(), members = members
        )
    }

    private fun notificationMembers(groupEntities: GroupEntities) {
        members.filter { it != sessionManager.getUser() }.forEach { member ->
            member.token?.let {
                notificationService.sendRemoteNotification(
                    it,
                    "Bem-vindo ao Grupo ${groupEntities.name}",
                    "Agora você faz parte do grupo ${groupEntities.name} para organizar as contas!"
                )
            }
        }
    }
}