package br.com.brunocarvalhs.extracts.extracts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.extracts.R
import br.com.brunocarvalhs.extracts.databinding.ItemExtractBinding
import br.com.brunocarvalhs.extracts.databinding.ItemMonthHeaderBinding
import com.google.android.material.card.MaterialCardView

class ExtractRecyclerViewAdapter(
    private val context: Context,
    private val listener: ExtractClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_COST = 1
    }

    private var items: List<Any> = emptyList()
    private var filteredItems: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemMonthHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_COST -> {
                val binding = ItemExtractBinding.inflate(inflater, parent, false)
                CostViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredItems[position]

        when (holder) {
            is HeaderViewHolder -> {
                val headerItem = item as String
                holder.monthYear.text = headerItem
            }
            is CostViewHolder -> {
                val costItem = item as CostEntities
                holder.root.setOnClickListener { listener.onClick(costItem) }
                holder.name.text = costItem.name
                holder.prompt.text = context.getString(
                    R.string.fragment_extracts_item_cost_date,
                    costItem.datePayment.toString()
                )
                holder.value.text = context.getString(R.string.item_cost_value, costItem.formatValue())
            }
        }
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun getItemViewType(position: Int): Int {
        return when (filteredItems[position]) {
            is String -> VIEW_TYPE_HEADER
            is CostEntities -> VIEW_TYPE_COST
            else -> throw IllegalArgumentException("Invalid item type at position $position")
        }
    }

    fun submitList(list: List<CostEntities>) {
        val headers = list.groupBy { it.dateReferenceMonth }.keys.filterNotNull()
        val flatList: MutableList<Any> = mutableListOf()
        headers.forEach { header ->
            flatList.add(header)
            val items = list.filter { it.dateReferenceMonth == header }
            flatList.addAll(items)
        }
        items = flatList
        filteredItems = flatList
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        filteredItems = if (text.isEmpty()) {
            items
        } else {
            items.filter {
                when (it) {
                    is CostEntities -> it.name?.contains(text, ignoreCase = true) == true
                    else -> false
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(binding: ItemMonthHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val monthYear: AppCompatTextView = binding.monthHeader
    }

    inner class CostViewHolder(binding: ItemExtractBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root: MaterialCardView = binding.root
        val name: AppCompatTextView = binding.name
        val prompt: AppCompatTextView = binding.prompt
        val value: AppCompatTextView = binding.value
    }

    interface ExtractClickListener {
        fun onClick(cost: CostEntities)
    }
}