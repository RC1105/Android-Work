package com.example.tictactoe

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.databinding.RecBinding
import kotlinx.android.synthetic.main.rec.view.*


class MainAdapter   (val RulesList: List<Rules>): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
    inner class MainViewHolder(val itemBinding: RecBinding ):RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(rules: Rules){
            itemBinding.recImage.setImageResource(rules.image)
            itemBinding.recText.text=rules.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        return MainViewHolder(RecBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        val rule=RulesList[position]
        holder.bindItem(rule)
    }

    override fun getItemCount(): Int {
        return RulesList.size
    }
}