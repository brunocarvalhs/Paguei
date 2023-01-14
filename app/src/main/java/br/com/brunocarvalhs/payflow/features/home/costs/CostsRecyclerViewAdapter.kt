package br.com.brunocarvalhs.payflow.features.home.costs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.payflow.databinding.FragmentCostsBinding
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CostsRecyclerViewAdapter(
    private val values: List<CostsEntities> = emptyList(),
    private val callback: (CostsEntities) -> Unit
) : RecyclerView.Adapter<CostsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCostsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { callback.invoke(item) }
        holder.idView.text = item.id
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCostsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}