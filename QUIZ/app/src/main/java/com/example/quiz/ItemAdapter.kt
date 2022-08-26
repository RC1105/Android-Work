package com.example.quiz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.ItemsRowBinding


class ItemAdapter(
    private val items: ArrayList<EmployeeEntity>
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvRank.text=(position+1).toString()
        holder.tvName.text = item.name
        holder.tvEmail.text = item.email
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.llMain.setBackgroundColor(Color.parseColor("#EFEFEF"))
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llMain = binding.llMain
        val tvName = binding.tvName
        val tvEmail = binding.Rating
        val tvRank= binding.tvRank

    }
}
