package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.vo.DmsDigitVo
import com.google.android.material.card.MaterialCardView
import java.math.BigDecimal

class DigitAdapter(context: Context?, records: List<DmsDigitVo>) : RecyclerView.Adapter<DigitAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var records: List<DmsDigitVo> = records

    fun setRecords(records: List<DmsDigitVo>) {
        this.records = records
    }

    fun updateRecords(records: List<DmsDigitVo>) {
        val recordMap = records.associateBy { it.num }
        this.records.forEach {
            it.amount = BigDecimal.ZERO
            val _record = recordMap[it.num]
            val record = _record ?: return@forEach
            it.area = record.area
            it.amount = record.amount
        }
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.digit_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.value.text = record.num.toString()
        holder.card.strokeColor = ContextCompat.getColor(holder.card.context, borderColorFor(record.color ?: ""))
    }

    private fun borderColorFor(value: String): Int = when (value) {
        "R" -> R.color.dashboard_item_border_red
        "G" -> R.color.dashboard_item_border_green
        "B" -> R.color.dashboard_item_border_blue
        else -> R.color.dashboard_item_border_default
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view as MaterialCardView
        val value: TextView = view.findViewById(R.id.digit_value)
    }
}