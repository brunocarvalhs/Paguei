package br.com.brunocarvalhs.calculation.cost_resume

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.calculation.databinding.ItemCalculationCostsBinding
import br.com.brunocarvalhs.domain.entities.CostEntities

class CalculationCostsRecyclerViewAdapter(
    private val context: Context,
) : RecyclerView.Adapter<CalculationCostsRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<CostEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCalculationCostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.value.text = item.formatValue()
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<CostEntities>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemCalculationCostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name
        val value = binding.value
    }
}