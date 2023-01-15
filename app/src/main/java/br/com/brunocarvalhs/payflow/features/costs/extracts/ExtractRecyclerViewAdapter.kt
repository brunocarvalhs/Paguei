package br.com.brunocarvalhs.payflow.features.costs.extracts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.payflow.R
import br.com.brunocarvalhs.payflow.databinding.ItemExtractBinding
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities

class ExtractRecyclerViewAdapter(
    private val context: Context,
    private val values: List<CostsEntities> = emptyList(),
    private val listener: ExtractClickListener,
) : RecyclerView.Adapter<ExtractRecyclerViewAdapter.ViewHolder>() {

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

    override fun getItemCount(): Int = values.size

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