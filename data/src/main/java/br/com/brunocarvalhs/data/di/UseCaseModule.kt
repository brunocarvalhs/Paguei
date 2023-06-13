package br.com.brunocarvalhs.data.di

import br.com.brunocarvalhs.data.usecase.auth.*
import br.com.brunocarvalhs.data.usecase.cost.*
import br.com.brunocarvalhs.data.usecase.group.*
import br.com.brunocarvalhs.data.usecase.report.*
import br.com.brunocarvalhs.domain.usecase.auth.*
import br.com.brunocarvalhs.domain.usecase.cost.*
import br.com.brunocarvalhs.domain.usecase.group.*
import br.com.brunocarvalhs.domain.usecase.report.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // Auth

    @Provides
    fun useCaseAuthenticateUserUseCase(useCase: AuthenticateUserUseCaseImpl): AuthenticateUserUseCase =
        useCase

    @Provides
    fun useCaseGetUserForEmailUseCase(useCase: GetUserForEmailUseCaseImpl): GetUserForEmailUseCase =
        useCase

    @Provides
    fun useCaseGetUserForIdUseCase(useCase: GetUserForIdUseCaseImpl): GetUserForIdUseCase =
        useCase

    @Provides
    fun useCaseGetUserFromSessionUseCase(useCase: GetUserFromDatabaseSessionUseCaseImpl): GetUserFromDatabaseSessionUseCase =
        useCase

    @Provides
    fun useCaseLogoutUserUseCase(useCase: LogoutUserUseCaseImpl): LogoutUserUseCase =
        useCase

    @Provides
    fun useCaseUpdateUserUseCase(useCase: UpdateUserUseCaseImpl): UpdateUserUseCase =
        useCase

    @Provides
    fun useCaseGetGroupSessionUseCase(useCase: GetGroupSessionUseCaseImpl): GetGroupSessionUseCase =
        useCase

    @Provides
    fun useCaseGetUserSessionUseCase(useCase: GetUserSessionUseCaseImpl): GetUserSessionUseCase =
        useCase

    @Provides
    fun useCaseDeleteUserUseCase(useCase: DeleteUserUseCaseImpl): DeleteUserUseCase =
        useCase

    // Cost

    @Provides
    fun useCaseAddCostUseCase(useCase: AddCostUseCaseImpl): AddCostUseCase = useCase

    @Provides
    fun useCaseFetchCostsUseCase(useCase: FetchCostsUseCaseImpl): FetchCostsUseCase = useCase

    @Provides
    fun useCaseDeleteCostUseCase(useCase: DeleteCostUseCaseImpl): DeleteCostUseCase = useCase

    @Provides
    fun useCaseGetCostUseCase(useCase: GetCostUseCaseImpl): GetCostUseCase = useCase

    @Provides
    fun useCaseUpdateCostUseCase(useCase: UpdateCostUseCaseImpl): UpdateCostUseCase = useCase

    @Provides
    fun useCaseFetchExtractsCostsUseCase(useCase: FetchExtractsCostsUseCaseImpl): FetchExtractsCostsUseCase =
        useCase

    // Group

    @Provides
    fun useCaseAddGroupUseCase(useCase: AddGroupUseCaseImpl): AddGroupUseCase = useCase

    @Provides
    fun useCaseDeleteGroupUseCase(useCase: DeleteGroupUseCaseImpl): DeleteGroupUseCase = useCase

    @Provides
    fun useCaseFetchGroupsUseCase(useCase: FetchGroupsUseCaseImpl): FetchGroupsUseCase = useCase

    @Provides
    fun useCaseGetGroupUseCase(useCase: GetGroupUseCaseImpl): GetGroupUseCase = useCase

    @Provides
    fun useCaseUpdateGroupUseCase(useCase: UpdateGroupUseCaseImpl): UpdateGroupUseCase = useCase

    // Report

    @Provides
    fun useCaseCalculateExpenseFrequencyUseCase(useCase: CalculateExpenseFrequencyUseCaseImpl): CalculateExpenseFrequencyUseCase =
        useCase

    @Provides
    fun useCaseCalculateExpensesByCategoryUseCase(useCase: CalculateExpensesByCategoryUseCaseImpl): CalculateExpensesByCategoryUseCase =
        useCase

    @Provides
    fun useCaseCalculateMonthlyExpensesUseCase(useCase: CalculateMonthlyExpensesUseCaseImpl): CalculateMonthlyExpensesUseCase =
        useCase

    @Provides
    fun useCaseCalculatePaymentPromptnessUseCase(useCase: CalculatePaymentPromptnessUseCaseImpl): CalculatePaymentPromptnessUseCase =
        useCase
}