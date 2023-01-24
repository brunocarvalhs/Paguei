package br.com.brunocarvalhs.paguei.features.costs.costs_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.ItemCostsBinding

class CostsRecyclerViewAdapter(
    private val context: Context,
    private val listener: CostClickListener,
) : RecyclerView.Adapter<CostsRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<CostsEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.root.setOnLongClickListener { listener.onLongClick(item) }
        holder.name.text = item.name
        holder.prompt.text = context.getString(R.string.item_cost_date, item.prompt.toString())
        holder.value.text = context.getString(R.string.item_cost_value, item.formatValue())
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<CostsEntities>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemCostsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name
        val prompt = binding.prompt
        val value = binding.value
    }

    interface CostClickListener {
        fun onClick(cost: CostsEntities)

        fun onLongClick(cost: CostsEntities): Boolean
    }
}