package br.com.brunocarvalhs.costs.costs_list

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.costs.databinding.ItemCostsBinding
import br.com.brunocarvalhs.domain.entities.CostEntities

class CostsRecyclerViewAdapter(
    private val context: Context,
    private val listener: CostClickListener,
) : RecyclerView.Adapter<CostsRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<CostEntities>()

    val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.layoutPosition
                val item = values[position]
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> listener.onSwipeLeft(item, position)
                    ItemTouchHelper.RIGHT -> listener.onSwipeRight(item, position)
                }
                notifyItemChanged(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top
                val isSwipeToLeft = dX < 0

                val background =
                    if (isSwipeToLeft) ColorDrawable(Color.rgb(190, 0, 0))
                    else ColorDrawable(Color.rgb(0, 100, 0))
                val icon = if (isSwipeToLeft) ContextCompat.getDrawable(
                    context, R.drawable.ic_baseline_delete_24
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_baseline_payment_24)

                background.setBounds(
                    if (isSwipeToLeft) itemView.right + dX.toInt() else itemView.left,
                    itemView.top,
                    if (isSwipeToLeft) itemView.right else itemView.left + dX.toInt(),
                    itemView.bottom
                )
                background.draw(c)

                icon?.let {
                    DrawableCompat.setTint(it, Color.WHITE)

                    val iconMargin = (itemHeight - it.intrinsicHeight) / 2
                    val iconTop = itemView.top + (itemHeight - it.intrinsicHeight) / 2
                    val iconBottom = iconTop + it.intrinsicHeight

                    it.setBounds(
                        if (isSwipeToLeft) itemView.right - iconMargin - it.intrinsicWidth else itemView.left + iconMargin,
                        iconTop,
                        if (isSwipeToLeft) itemView.right - iconMargin else itemView.left + iconMargin + it.intrinsicWidth,
                        iconBottom
                    )
                    it.draw(c)
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.root.setOnClickListener { listener.onClick(item) }
        holder.root.setOnLongClickListener { listener.onLongClick(item) }
        holder.icon.setOnClickListener { listener.onLongClick(item) }
        holder.name.text = item.name
        holder.prompt.text = context.getString(R.string.item_cost_date, item.prompt.toString())
        holder.value.text = context.getString(R.string.item_cost_value, item.formatValue())
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<CostEntities>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(binding: ItemCostsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val name = binding.name
        val prompt = binding.prompt
        val value = binding.value
        val icon = binding.icon
    }

    interface CostClickListener {
        fun onClick(cost: CostEntities)

        fun onLongClick(cost: CostEntities): Boolean

        fun onSwipeLeft(costEntities: CostEntities, position: Int)

        fun onSwipeRight(costEntities: CostEntities, position: Int)
    }
}