package br.com.brunocarvalhs.calculation.accounts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.calculation.databinding.ItemCalculationMembersBinding
import br.com.brunocarvalhs.domain.entities.UserEntities
import com.bumptech.glide.Glide

class CalculationAccountsMembersRecyclerViewAdapter(
    private val context: Context,
    private val listener: CalculationAccountsMembersClickListener,
) : RecyclerView.Adapter<CalculationAccountsMembersRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<UserEntities>()
    private val listSelects = mutableListOf<UserEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCalculationMembersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        item?.let {
            holder.bind(item)
        } ?: kotlin.run { holder.root.isEnabled = false }
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<UserEntities>) {
        values.clear()
        values.addAll(list)
        listSelects.clear()
        listSelects.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemCalculationMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        private val name = binding.name
        private val avatar = binding.avatar
        private val avatarText = binding.avatarText
        private val salary = binding.salary

        fun bind(user: UserEntities) {
            root.isChecked = true

            user.salary?.let {
                root.setOnLongClickListener {
                    root.isChecked = !root.isChecked
                    if (root.isChecked) {
                        listSelects.add(user)
                    } else {

                        listSelects.remove(user)
                    }
                    listener.onLongClickListener(listSelects)
                }
            }

            root.isChecked = !user.salary.isNullOrEmpty()

            if (user.photoUrl.isNullOrEmpty()) {
                avatar.visibility = View.GONE
                avatarText.visibility = View.VISIBLE
                avatarText.text = user.initialsName()
            } else {
                Glide.with(context).load(user.photoUrl).centerCrop().into(avatar)
            }

            name.text = user.name
            salary.text = user.salary
        }
    }

    interface CalculationAccountsMembersClickListener {
        fun onLongClickListener(user: MutableList<UserEntities>): Boolean
    }
}