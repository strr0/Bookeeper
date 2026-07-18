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

class DigitAdapter(context: Context?, private var records: List<DmsDigitVo>) : RecyclerView.Adapter<DigitAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context = context!!
    private var lv1: BigDecimal = BigDecimal.ZERO
    private var lv2: BigDecimal = BigDecimal.ZERO
    private var lv3: BigDecimal = BigDecimal.ZERO
    private var lv4: BigDecimal = BigDecimal.ZERO
    private var lv5: BigDecimal = BigDecimal.ZERO

    fun updateRecords(records: List<DmsDigitVo>) {
        val recordMap = records.associateBy { it.num }
        if (records.isNotEmpty()) {
            val max = records.maxBy { it.amount }.amount
            this.lv1 = max.multiply(BigDecimal("0.2"))
            this.lv2 = max.multiply(BigDecimal("0.4"))
            this.lv3 = max.multiply(BigDecimal("0.6"))
            this.lv4 = max.multiply(BigDecimal("0.8"))
            this.lv5 = max
        }
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
        if (record.amount.compareTo(BigDecimal.ZERO) > 0) {
            holder.value.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.value.background = ContextCompat.getDrawable(context, backgroudColorFor(record.amount))
        }
    }

    private fun borderColorFor(value: String): Int = when (value) {
        "R" -> R.color.dashboard_item_border_red
        "G" -> R.color.dashboard_item_border_green
        "B" -> R.color.dashboard_item_border_blue
        else -> R.color.dashboard_item_border_default
    }

    private fun backgroudColorFor(value: BigDecimal): Int {
        if (value.compareTo(lv1) <= 0) {
            return R.color.red_50
        }
        if (value.compareTo(lv2) <= 0) {
            return R.color.red_100
        }
        if (value.compareTo(lv3) <= 0) {
            return R.color.red_150
        }
        if (value.compareTo(lv4) <= 0) {
            return R.color.red_200
        }
        if (value.compareTo(lv5) <= 0) {
            return R.color.red_250
        }
        return R.color.common_card_bg
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view as MaterialCardView
        val value: TextView = view.findViewById(R.id.digit_value)
    }
}