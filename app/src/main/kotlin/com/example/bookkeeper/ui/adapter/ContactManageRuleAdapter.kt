package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.vo.RmsRuleVo
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ContactManageRuleAdapter(context: Context?) : RecyclerView.Adapter<ContactManageRuleAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val records: MutableList<RmsRuleVo> = ArrayList()
    private var onSaveClickListener: ((RmsRuleVo, String) -> Unit)? = null

    fun setRecords(records: List<RmsRuleVo>) {
        this.records.clear()
        this.records.addAll(records)
    }

    fun setOnSaveClickListener(listener: (RmsRuleVo, String) -> Unit) {
        onSaveClickListener = listener
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.contact_manage_rule_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.name.text = record.name.orEmpty()
        val times = record.times.toString()
        holder.times.setText(if (times.isEmpty()) "0" else times)
        holder.save.setOnClickListener {
            val value = holder.times.text.toString().trim()
            onSaveClickListener?.invoke(record, value)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.contact_manage_rule_name)
        val times: TextInputEditText = view.findViewById(R.id.contact_manage_rule_times)
        val save: MaterialButton = view.findViewById(R.id.contact_manage_rule_save)
    }
}
