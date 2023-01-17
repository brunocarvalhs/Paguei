package br.com.brunocarvalhs.paguei.features.costs.extracts

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.ItemExtractBinding
import br.com.brunocarvalhs.paguei.domain.entities.CostsEntities

class ExtractRecyclerViewAdapter(
    private val context: Context,
    private val values: List<CostsEntities> = emptyList(),
    private val listener: ExtractClickListener,
) : RecyclerView.Adapter<ExtractRecyclerViewAdapter.ViewHolder>() {

    private var filter: ItemFilter = ItemFilter()
    private var filteredValues: List<*> = values

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemExtractBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.name.text = item.name
        holder.prompt.text = context.getString(R.string.item_cost_date, item.prompt.toString())
        holder.value.text = context.getString(R.string.item_cost_value, item.value.toString())
    }

    override fun getItemCount(): Int = filteredValues.size

    fun getFilter(): Filter {
        return filter
    }

    inner class ViewHolder(binding: ItemExtractBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name
        val prompt = binding.prompt
        val value = binding.value

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    interface ExtractClickListener {
        fun onClick(cost: CostsEntities)
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = if (constraint == null || constraint.isEmpty()) {
                values
            } else {
                values.filter {
                    it.name?.contains(constraint, true) ?: false
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredValues = results?.values as List<*>
            notifyDataSetChanged()
        }
    }
}
