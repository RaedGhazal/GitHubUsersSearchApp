package com.raedghazal.githubuserssearchapp.presentation.users

import android.view.AbsSavedState
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raedghazal.githubuserssearchapp.MainActivity
import com.raedghazal.githubuserssearchapp.R
import com.raedghazal.githubuserssearchapp.databinding.UserItemLayoutBinding
import com.raedghazal.githubuserssearchapp.domain.model.User

class UsersAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<User, UsersAdapter.UserViewHolder>(PHOTO_COMPARATOR) {

    inner class UserViewHolder(private val binding: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null)
                        listener.onItemClick(item)
                }
            }
        }

        fun bind(user: User) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_error)
                    .into(imgUserAvatar)
                tvUserName.text = user.login
                tvType.text = user.type
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            UserItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

}