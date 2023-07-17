package br.com.brunocarvalhs.check_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.check_list.databinding.ItemCheckBinding

class CheckListRecyclerViewAdapter(
    private val context: Context,
) : RecyclerView.Adapter<CheckListRecyclerViewAdapter.ViewHolder>() {

    var values = mutableListOf<CheckListData>()
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        val options = item.values.map { (key, value) -> createCheckBox(key, value) }
        options.forEach { holder.checkbox.addView(it) }
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<CheckListData>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun createCheckBox(name: String?, state: Boolean): CheckBox {
        val check = CheckBox(context)
        check.text = name
        check.isChecked = state
        check.isEnabled = false
        return check
    }

    inner class ViewHolder(binding: ItemCheckBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name
        val checkbox = binding.checkboxContainer
    }

    data class CheckListData(
        val name: String, val values: Map<String?, Boolean>
    )
}