package br.com.brunocarvalhs.groups.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.groups.databinding.ItemGroupRegisterMembersBinding
import com.bumptech.glide.Glide

class GroupRegisterMembersRecyclerViewAdapter(
    private val context: Context,
    private val viewModel: GroupRegisterViewModel,
    private val listeners: GroupRegisterMembersListeners
) : RecyclerView.Adapter<GroupRegisterMembersRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<UserEntities>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemGroupRegisterMembersBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = values[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = values.size

    fun submitList(list: List<UserEntities>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    fun removeUser(user: UserEntities) {
        val index = values.indexOf(user)
        if (index != -1) {
            values.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(binding: ItemGroupRegisterMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name = binding.name
        private val avatar = binding.avatar
        private val avatarText = binding.avatarText
        private val icon: ImageView = binding.icon

        fun bind(user: UserEntities) {
            if (user.photoUrl.isNullOrEmpty()) {
                avatar.visibility = View.GONE
                avatarText.visibility = View.VISIBLE
                avatarText.text = user.initialsName()
            } else {
                Glide.with(context).load(user.photoUrl).centerCrop().into(avatar)
            }
            name.text = user.firstName()
            icon.visibility = if (viewModel.isIconCloseVisibility(user)) View.VISIBLE else View.GONE
            icon.setOnClickListener {
                listeners.onRemoveUser(user)
                values.remove(user)
            }
        }
    }

    interface GroupRegisterMembersListeners {
        fun onRemoveUser(member: UserEntities)
    }
}