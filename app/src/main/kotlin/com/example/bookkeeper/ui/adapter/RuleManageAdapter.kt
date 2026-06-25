package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.model.RmsRule
import com.example.bookkeeper.ui.common.AvatarUi

class RuleManageAdapter(context: Context?) : RecyclerView.Adapter<RuleManageAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val appContext: Context = context!!.applicationContext
    private val records: MutableList<RmsRule> = ArrayList()

    fun setRecords(records: List<RmsRule>) {
        this.records.clear()
        this.records.addAll(records)
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.rule_manage_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.name.text = record.name.orEmpty()
        val times = record.times.toString()
        holder.times.text = if (times.isEmpty()) "0" else times
        AvatarUi.bindAvatar(appContext, holder.avatar, record.name)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.rule_manage_avatar)
        val name: TextView = view.findViewById(R.id.rule_manage_name)
        val times: TextView = view.findViewById(R.id.rule_manage_times)
    }
}
