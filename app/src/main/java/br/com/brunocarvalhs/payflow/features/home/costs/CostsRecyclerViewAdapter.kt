package br.com.brunocarvalhs.payflow.features.home.costs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.payflow.R
import br.com.brunocarvalhs.payflow.databinding.ItemCostsBinding
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities

class CostsRecyclerViewAdapter(
    private val context: Context,
    private val values: List<CostsEntities> = emptyList(),
    private val listener: CostClickListener,
) : RecyclerView.Adapter<CostsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.root.setOnLongClickListener { listener.onLongClick(item) }
        holder.name.text = item.name
        holder.prompt.text = context.getString(R.string.item_cost_date, item.prompt.toString())
        holder.value.text = context.getString(R.string.item_cost_value, item.value.toString())
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemCostsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name
        val prompt = binding.prompt
        val value = binding.value

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    interface CostClickListener {
        fun onClick(cost: CostsEntities)

        fun onLongClick(cost: CostsEntities): Boolean
    }
}