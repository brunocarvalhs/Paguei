package br.com.brunocarvalhs.costs.costs_list

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.BaseComposeViewModel
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.cost.DeleteCostUseCase
import br.com.brunocarvalhs.domain.usecase.cost.FetchCostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val useCase: FetchCostsUseCase,
    private val deleteCostUseCase: DeleteCostUseCase,
    private val analyticsService: AnalyticsService,
    private val navigation: Navigation,
) : BaseComposeViewModel<CostsViewState>(CostsViewState.Loading) {

    val header: Header = Header(
        name = sessionManager.getGroup()?.name ?: sessionManager.getUser()?.firstName(),
        photoUrl = sessionManager.getUser()?.photoUrl,
        initials = sessionManager.getUser()?.initialsName(),
        isGroup = sessionManager.isGroupSession()
    )

    private var listCosts = mutableListOf<CostEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = CostsViewState.Loading
            useCase.invoke().onSuccess {
                listCosts = it.toMutableList()
                mutableState.value = CostsViewState.Success(listCosts)
            }.onFailure { error -> mutableState.value = CostsViewState.Error(error.message) }
        }
    }

    fun deleteCost(cost: CostEntities, callback: () -> Unit) {
        viewModelScope.launch {
            deleteCostUseCase.invoke(cost).onSuccess { callback.invoke() }
                .onFailure { error -> mutableState.value = CostsViewState.Error(error.message) }
        }
    }

    fun onSwipeRight(cost: CostEntities, navCostEntities: NavController) {
        val action = CostsFragmentDirections.actionHomeFragmentToItemListDialogFragment(cost)
        navCostEntities.navigate(action)
    }

    fun onSwipeLeft(cost: CostEntities, navCostEntities: NavController) {

    }

    fun onLongClick(cost: CostEntities, navCostEntities: NavController) {
        val action = CostsFragmentDirections.actionHomeFragmentToItemListDialogFragment(cost)
        navCostEntities.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.COST_ITEM_LONG_CLICKED,
            mapOf(Pair("cost_id", cost.id)),
            CostsFragment::class
        )

    }

    fun onClick(cost: CostEntities, navController: NavController) {
        navigateToReaderCost(cost, navController)

        analyticsService.trackEvent(
            AnalyticsService.Events.COST_ITEM_CLICKED,
            mapOf(Pair("cost_id", cost.id)),
            CostsFragment::class
        )
    }

    fun navigateToAddCosts(navCostEntities: NavController) {
        val action = navigation.navigateToBilletRegistrationForm()
        navCostEntities.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.ADD_COST_BUTTON_CLICKED, mapOf(), CostsFragment::class
        )
    }

    fun navigateToReaderCost(cost: CostEntities, navController: NavController) {
        val action = CostsFragmentDirections.actionCostsFragmentToCostReaderFragment(cost)
        navController.navigate(action)
    }

    fun navigateToGroups(navController: NavController) {
        val action = navigation.navigateToGroups()
        navController.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.EXTRACTS_MENU_SELECTED,
            mapOf(),
            CostsFragment::class
        )
    }

    fun navigateToCheckList(navController: NavController) {
        val action = navigation.navigateToCheckList()
        navController.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.CHECK_LIST_MENU_SELECTED, mapOf(), CostsFragment::class
        )
    }

    fun navigateToCalculation(navController: NavController) {
        val action = navigation.navigateToCalculation()
        navController.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.CALCULATION_MENU_SELECTED, mapOf(), CostsFragment::class
        )
    }

    fun navigateToProfile(navController: NavController) {
        val request = navigation.navigateToProfileRegister()
        navController.navigate(request)
    }

    fun navigateToExtracts(navController: NavController) {
        val action = navigation.navigateToExtractsList()
        navController.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.EXTRACTS_MENU_SELECTED, mapOf(), CostsFragment::class
        )
    }

    fun navigateToReport(navController: NavController) {
        val action = navigation.navigateToReport()
        navController.navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.REPORT_SCREEN_VIEWED, mapOf(), CostsFragment::class
        )
    }

    data class Header(
        val name: String?,
        val photoUrl: String? = null,
        val initials: String?,
        val isGroup: Boolean = false
    )
}
