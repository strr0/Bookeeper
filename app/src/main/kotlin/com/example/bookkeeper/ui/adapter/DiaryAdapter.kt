package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.ui.common.AvatarUi
import com.example.bookkeeper.data.vo.BmsBillVo
import com.example.bookkeeper.util.DateUtil
import com.google.android.material.chip.Chip

class DiaryAdapter(context: Context?) : RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val appContext: Context = context!!.applicationContext
    private val records: MutableList<BmsBillVo> = ArrayList()
    private var onChevronClickListener: ((Long) -> Unit)? = null

    fun setRecords(records: List<BmsBillVo>) {
        this.records.clear()
        this.records.addAll(records)
    }

    fun setOnChevronClickListener(listener: (Long) -> Unit) {
        onChevronClickListener = listener
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.diary_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.accName.text = record.accName.orEmpty()
        holder.remarks.text = record.remarks.orEmpty()
        holder.amount.text = record.amount?.stripTrailingZeros()?.toPlainString() ?: "0.00"
        holder.updateTime.text = record.updateTime?.let { DateUtil.formatDateTime(it) }.orEmpty()
        holder.area.setText(if (record.area.equals("hk")) R.string.common_area_hk else R.string.common_area_mo)
        AvatarUi.bindAvatar(appContext, holder.avatar, record.accName)
        holder.chevron.setOnClickListener {
            onChevronClickListener?.invoke(record.id)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.diary_avatar)
        val accName: TextView = view.findViewById(R.id.diary_name)
        val remarks: TextView = view.findViewById(R.id.diary_remarks)
        val amount: TextView = view.findViewById(R.id.diary_amount)
        val updateTime: TextView = view.findViewById(R.id.diary_time)
        val area: Chip = view.findViewById(R.id.diary_area_chip)
        val chevron: View = view.findViewById(R.id.diary_chevron)
    }
}
