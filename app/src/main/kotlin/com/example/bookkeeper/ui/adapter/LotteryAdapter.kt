package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.vo.LmsLotteryDetailVo
import com.google.android.material.card.MaterialCardView

class LotteryAdapter(context: Context?) : RecyclerView.Adapter<LotteryAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val records: List<LmsLotteryDetailVo> = (1..7).map { LmsLotteryDetailVo().apply {
        seq = it
        num = 0
    } }.toMutableList()

    fun updateRecords(records: List<LmsLotteryDetailVo>) {
        this.records.forEach {
            it.id = 0L
            it.lotId = 0L
            it.num = 0L
            it.zod = 0L
            it.area = null
            it.color = null
        }
        val recordMap = this.records.associateBy { it.seq }
        records.forEach {
            val _record = recordMap[it.seq]
            val record = _record ?: return@forEach
            record.id = it.id
            record.lotId = it.lotId
            record.num = it.num
            record.zod = it.zod
            record.area = it.area
            record.color = it.color
        }
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.lottery_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.value.text = record.num?.toString().orEmpty()
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
        val value: TextView = view.findViewById(R.id.lottery_value)
    }
}
