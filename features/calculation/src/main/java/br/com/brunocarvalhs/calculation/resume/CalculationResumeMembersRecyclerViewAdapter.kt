package br.com.brunocarvalhs.calculation.resume

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.calculation.databinding.ItemCalculationResumeMembersBinding
import br.com.brunocarvalhs.domain.entities.UserEntities
import com.bumptech.glide.Glide

class CalculationResumeMembersRecyclerViewAdapter(
    private val context: Context,
) : RecyclerView.Adapter<CalculationResumeMembersRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableMapOf<UserEntities, Double>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCalculationResumeMembersBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = values.entries.elementAt(position)
        holder.bind(entry)
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: HashMap<UserEntities, Double>) {
        values.clear()
        values.putAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemCalculationResumeMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name = binding.name
        private val avatar = binding.avatar
        private val avatarText = binding.avatarText
        private val salary = binding.salary
        private val percent = binding.percentage

        fun bind(entry: Map.Entry<UserEntities, Double>) {
            val user = entry.key
            val percentage = entry.value

            if (user.photoUrl.isNullOrEmpty()) {
                avatar.visibility = View.GONE
                avatarText.visibility = View.VISIBLE
                avatarText.text = user.initialsName()
            } else {
                Glide.with(context).load(user.photoUrl).centerCrop().into(avatar)
            }
            name.text = user.firstName()
            salary.text =
                String.format("%.2f", ((percentage / 100.0) * (user.salary?.toDouble() ?: 0.0)))
            percent.text = "${String.format(" % .2f", percentage)}%"
        }
    }
}