package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.vo.DmsZodiacVo
import java.math.BigDecimal

class ZodiacAdapter(context: Context?, records: List<DmsZodiacVo>) : RecyclerView.Adapter<ZodiacAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var records: List<DmsZodiacVo> = records

    fun setRecords(records: List<DmsZodiacVo>) {
        this.records = records
    }

    fun updateRecords(records: List<DmsZodiacVo>) {
        val recordMap = records.associateBy { it.code }
        this.records.forEach {
            it.amount = BigDecimal.ZERO
            val _record = recordMap[it.code]
            val record = _record ?: return@forEach
            it.area = record.area
            it.amount = record.amount
        }
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.zodiac_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.value.text = record.name.toString()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val value: TextView = view.findViewById(R.id.zodiac_value)
    }
}