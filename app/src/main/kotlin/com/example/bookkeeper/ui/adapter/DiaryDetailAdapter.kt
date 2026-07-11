package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.vo.BmsBillDetailVo
import com.example.bookkeeper.data.vo.DmsDigitVo
import com.example.bookkeeper.data.vo.DmsZodiacVo
import com.example.bookkeeper.util.DateUtil

class DiaryDetailAdapter(context: Context?, digits: List<DmsDigitVo>, zodiacs: List<DmsZodiacVo>) : RecyclerView.Adapter<DiaryDetailAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context = context!!
    private val digitMap = digits.associateBy { it.id }
    private val zodiacMap = zodiacs.associateBy { it.id }
    private val records: MutableList<List<BmsBillDetailVo>> = ArrayList()

    fun setRecords(records: List<BmsBillDetailVo>) {
        this.records.clear()
        val typeRecord = records.groupBy { it.type }
        typeRecord["1"]?.forEach { this.records.add(listOf(it)) }
        typeRecord["2"]?.groupBy { it.cluster }?.map { (_, v) -> this.records.add(v) }
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.diary_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        if (record.size > 1) {
            val zodiacs = record.map {
                zodiacMap[it.item]!!
            }
            holder.itemList.adapter = ZodiacAdapter(context, zodiacs)
        } else {
            val digits = record.map {
                digitMap[it.item]!!
            }
            holder.itemList.adapter = DigitAdapter(context, digits)
        }
        val detail = record[0]
        holder.amount.text = detail.price?.stripTrailingZeros()?.toPlainString().orEmpty()
        holder.updateTime.text = detail.updateTime?.let { DateUtil.formatDateTime(it) }.orEmpty()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemList: RecyclerView = view.findViewById(R.id.diary_detail_item_list)
        val amount: TextView = view.findViewById(R.id.diary_detail_amount)
        val updateTime: TextView = view.findViewById(R.id.diary_detail_time)
    }
}
