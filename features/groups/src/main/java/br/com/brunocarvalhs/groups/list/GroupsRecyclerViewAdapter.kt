package br.com.brunocarvalhs.groups.list

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
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.groups.R
import br.com.brunocarvalhs.groups.databinding.ItemGroupsBinding

class GroupsRecyclerViewAdapter(
    private val context: Context,
    private val listener: GroupsClickListener,
) : RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<GroupEntities>()

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
                    context, R.drawable.ic_baseline_exit_to_app_24
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_baseline_edit_note_24)

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

    fun removeItem(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
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
        fun onSwipeLeft(item: GroupEntities, position: Int)
        fun onSwipeRight(item: GroupEntities, position: Int)
    }
}