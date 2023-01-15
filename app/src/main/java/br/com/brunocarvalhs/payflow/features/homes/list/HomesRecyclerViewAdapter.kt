package br.com.brunocarvalhs.payflow.features.homes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.payflow.databinding.ItemHomesBinding

class HomesRecyclerViewAdapter(
    private val values: List<String> = emptyList(),
    private val listener: HomesClickListener,
) : RecyclerView.Adapter<HomesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemHomesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.root.setOnLongClickListener { listener.onLongClick(item) }
        holder.name.text = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemHomesBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    interface HomesClickListener {
        fun onClick(home: String)

        fun onLongClick(home: String): Boolean
    }
}