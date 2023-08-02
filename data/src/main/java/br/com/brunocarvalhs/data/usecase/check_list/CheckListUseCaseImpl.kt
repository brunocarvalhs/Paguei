package br.com.brunocarvalhs.data.usecase.check_list

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.check_list.CheckListUseCase
import br.com.brunocarvalhs.domain.usecase.cost.FetchCostsUseCase
import br.com.brunocarvalhs.domain.usecase.cost.FetchExtractsCostsUseCase
import java.text.ParseException
import java.util.Locale
import javax.inject.Inject

class CheckListUseCaseImpl @Inject constructor(
    private val fetchExtractsCostsUseCase: FetchExtractsCostsUseCase,
    private val fetchCostsUseCase: FetchCostsUseCase,
) : CheckListUseCase {
    override suspend fun invoke(): Result<HashMap<String, Map<String?, Boolean>>> {
        return try {
            val list: MutableList<CostEntities> = mutableListOf()
            fetchCostsUseCase.invoke().getOrNull()?.let { list.addAll(it) }
            fetchExtractsCostsUseCase.invoke().getOrNull()?.let { list.addAll(it) }

            val filteredObjects = filterCosts(list)
            val groupedObjects = groupCostsByName(filteredObjects)
            val sortedGroups = sortGroupsByName(groupedObjects)

            val hashMap = buildHashMap(sortedGroups)

            Result.success(hashMap)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    private fun filterCosts(costs: List<CostEntities>): List<CostEntities> {
        return costs.filter { it.type == "FIX" && it.name != null }
    }

    private fun groupCostsByName(costs: List<CostEntities>): Map<String?, List<CostEntities>> {
        return costs.groupBy { it.name }
    }

    private fun sortGroupsByName(groups: Map<String?, List<CostEntities>>): Map<String?, List<CostEntities>> {
        return groups.toSortedMap(compareBy { it })
    }

    private fun buildHashMap(
        groups: Map<String?, List<CostEntities>>,
    ): HashMap<String, Map<String?, Boolean>> {
        val hashMap = HashMap<String, Map<String?, Boolean>>()
        for ((name, group) in groups) {
            val monthMap = buildMonthMap(group)
            hashMap[name ?: ""] = monthMap
        }
        return hashMap
    }

    private fun buildMonthMap(
        group: List<CostEntities>,
    ): Map<String?, Boolean> {
        val monthMap = linkedMapOf<String?, Boolean>()
        addAllMonthsToMap(monthMap, group)
        updateMonthMapWithCurrentMonth(monthMap, group)
        return monthMap
    }

    private fun addAllMonthsToMap(
        monthMap: MutableMap<String?, Boolean>,
        group: List<CostEntities>
    ) {
        val currentMonth = getCurrentMonth()
        val firstMonth =
            group.minByOrNull { parseMonthYear(it.dateReferenceMonth) }?.dateReferenceMonth
        val lastMonth =
            group.maxByOrNull { parseMonthYear(it.dateReferenceMonth) }?.dateReferenceMonth

        if (firstMonth != null && lastMonth != null) {
            val firstMonthCalendar = parseMonthYear(firstMonth)
            val lastMonthCalendar = parseMonthYear(lastMonth)
            val calendar = firstMonthCalendar.clone() as Calendar
            while (calendar <= lastMonthCalendar) {
                val month = formatMonthYear(calendar)
                monthMap[month] = false
                calendar.add(Calendar.MONTH, 1)
            }
        }

        val firstMonthCalendar = parseMonthYear(firstMonth)
        if (firstMonthCalendar != null) {
            val calendar = firstMonthCalendar.clone() as Calendar
            calendar.add(Calendar.MONTH, -1)
            while (calendar >= currentMonth) {
                val month = formatMonthYear(calendar)
                monthMap[month] = false
                calendar.add(Calendar.MONTH, -1)
            }
        }

        val lastMonthCalendar = parseMonthYear(lastMonth)
        if (lastMonthCalendar != null) {
            val calendar = lastMonthCalendar.clone() as Calendar
            calendar.add(Calendar.MONTH, 1)
            while (calendar <= currentMonth) {
                val month = formatMonthYear(calendar)
                monthMap[month] = false
                calendar.add(Calendar.MONTH, 1)
            }
        }

        val currentMonthString = formatMonthYear(currentMonth)
        monthMap[currentMonthString] = monthMap[currentMonthString] ?: false
    }

    private fun updateMonthMapWithCurrentMonth(
        monthMap: MutableMap<String?, Boolean>,
        group: List<CostEntities>,
    ) {
        for (obj in group) {
            val month = obj.dateReferenceMonth
            val hasDataPayment = hasDataPayment(obj)
            monthMap[month] = hasDataPayment
        }
    }

    private fun getCurrentMonth(): Calendar {
        val currentMonth = Calendar.getInstance()
        currentMonth[Calendar.DAY_OF_MONTH] = 1
        return currentMonth
    }

    private fun hasDataPayment(cost: CostEntities): Boolean {
        return cost.datePayment != null
    }

    private fun parseMonthYear(monthYear: String?): Calendar {
        val format = SimpleDateFormat("MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()

        try {
            val date = if (monthYear != null) format.parse(monthYear) else null
            if (date != null) {
                calendar.time = date
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return calendar
    }

    private fun formatMonthYear(calendar: Calendar): String {
        val format = SimpleDateFormat("MM/yyyy", Locale.getDefault())
        return format.format(calendar.time)
    }
}
