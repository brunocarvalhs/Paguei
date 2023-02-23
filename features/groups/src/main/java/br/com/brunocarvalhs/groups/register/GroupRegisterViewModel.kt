package br.com.brunocarvalhs.groups.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.repositories.HomesRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.GetUserForEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject constructor(
    private val repository: HomesRepository,
    private val sessionManager: SessionManager,
    private val getUserForEmailUseCase: GetUserForEmailUseCase,
) : BaseViewModel<GroupRegisterViewState>() {

    val name = ObservableField<String>()

    val member = ObservableField<String>()

    var members = mutableListOf<UserEntities>()
        private set

    init {
        sessionManager.getUser()?.let { members.add(it) }
    }

    fun registerMember() {
        member.get()?.let { email ->
            viewModelScope.launch {
                try {
                    mutableState.value = GroupRegisterViewState.Loading
                    getUserForEmailUseCase(email)?.let { userEntities ->
                        if (!members.contains(userEntities)) {
                            members.add(userEntities)
                        }
                    }
                    mutableState.value = GroupRegisterViewState.MemberSearchSuccess
                } catch (error: Exception) {
                    mutableState.value = GroupRegisterViewState.Error(error.message)
                }
            }
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

    private fun save(homes: GroupEntities) {
        viewModelScope.launch {
            try {
                mutableState.value = GroupRegisterViewState.Loading
                repository.add(homes)
                sessionManager.sessionGroup(homes)
                mutableState.value = GroupRegisterViewState.Success
            } catch (error: Exception) {
                mutableState.value = GroupRegisterViewState.Error(error.message)
            }
        }
    }

    fun createHomes() {
        val group = generateHomes()
        save(group)
    }

    private fun generateHomes(): GroupEntities {
        val members = members.map { it.id }
        return GroupsModel(
            name = name.get(),
            members = members
        )
    }
}