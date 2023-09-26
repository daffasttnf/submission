package com.dicoding.submission.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission.R
import com.dicoding.submission.data.response.ItemsItem

class UserAdapter(private var listUser: List<ItemsItem>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvUser.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl) // URL Gambar
            .circleCrop()
            .into(holder.imageUser) // imageView mana yang akan diterapkan
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, user.login)
            holder.itemView.context.startActivity(intentDetail)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUser: ImageView = itemView.findViewById(R.id.image_user)
        val tvUser: TextView = itemView.findViewById(R.id.tv_user)
    }
}