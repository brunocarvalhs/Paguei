package br.com.brunocarvalhs.extracts.extracts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.extracts.R
import br.com.brunocarvalhs.extracts.databinding.ItemExtractBinding

class ExtractRecyclerViewAdapter(
    private val context: Context,
    private val listener: ExtractClickListener,
) : RecyclerView.Adapter<ExtractRecyclerViewAdapter.ViewHolder>() {

    private var values = listOf<CostsEntities>()
    private var filteredValues = listOf<CostsEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemExtractBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredValues[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.name.text = item.name
        holder.prompt.text = context.getString(R.string.fragment_extracts_item_cost_date, item.datePayment.toString())
        holder.value.text = context.getString(R.string.item_cost_value, item.formatValue())
    }

    override fun getItemCount(): Int = filteredValues.size

    fun submitList(list: List<CostsEntities>) {
        values = list
        filteredValues = list
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        filteredValues = if (text.isEmpty()) {
            values
        } else {
            values.filter { it.name?.contains(text, ignoreCase = true) == true }
        }
        notifyDataSetChanged()
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
}