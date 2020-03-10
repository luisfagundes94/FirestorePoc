package com.luisfelipe.firebasetp3.presentation.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luisfelipe.firebasetp3.R
import com.luisfelipe.firebasetp3.domain.model.User
import com.luisfelipe.firebasetp3.utils.OnItemClickListener

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var users = listOf<User>()
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user, parent, false)
        return UserListViewHolder(view, onItemClickListener)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) =
        holder.bind(users[position])

    class UserListViewHolder(
        itemView: View,
        private val onItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val mobilePhone = itemView.findViewById<TextView>(R.id.mobile_phone)

        fun bind(user: User) {
            name.text = user.name
            mobilePhone.text = user.mobilePhone

            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
        }

    }

    fun updateUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}