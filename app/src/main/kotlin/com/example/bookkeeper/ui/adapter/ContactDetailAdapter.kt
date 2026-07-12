package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.model.AmsAccountDetail
import com.example.bookkeeper.util.DateUtil
import com.google.android.material.chip.Chip

class ContactDetailAdapter(context: Context?) : RecyclerView.Adapter<ContactDetailAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val records: MutableList<AmsAccountDetail> = ArrayList()

    fun setRecords(records: List<AmsAccountDetail>) {
        this.records.clear()
        this.records.addAll(records)
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.contact_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.remarks.text = record.remarks.orEmpty()
        holder.amount.text = record.amount?.stripTrailingZeros()?.toPlainString() ?: "0.00"
        holder.updateTime.text = record.updateTime?.let { DateUtil.formatDateTime(it) }.orEmpty()
        holder.area.setText(if (record.area.equals("hk")) R.string.common_area_hk else R.string.common_area_mo)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val remarks: TextView = view.findViewById(R.id.contact_remarks)
        val amount: TextView = view.findViewById(R.id.contact_amount)
        val updateTime: TextView = view.findViewById(R.id.contact_time)
        val area: Chip = view.findViewById(R.id.contact_area_chip)
    }
}
