package br.com.brunocarvalhs.groups.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.groups.databinding.ItemGroupsBinding

class GroupsRecyclerViewAdapter(
    private val context: Context,
    private val listener: GroupsClickListener,
) : RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<GroupEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.name.text = item.name
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<GroupEntities>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemGroupsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }
    }

    interface GroupsClickListener {
        fun onClick(group: GroupEntities?)
    }
}