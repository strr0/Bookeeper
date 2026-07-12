package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.ui.common.AvatarUi
import com.example.bookkeeper.util.DateUtil

class ContactAdapter(context: Context?) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val appContext: Context = context!!.applicationContext
    private val records: MutableList<AmsAccount> = ArrayList()
    private var onChevronClickListener: ((AmsAccount) -> Unit)? = null

    fun setRecords(records: List<AmsAccount>) {
        this.records.clear()
        this.records.addAll(records)
    }

    fun setOnChevronClickListener(listener: (AmsAccount) -> Unit) {
        onChevronClickListener = listener
    }

    override fun getItemCount(): Int = records.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.name.text = record.name.orEmpty()
        val phone = record.phone?.trim().orEmpty()
        holder.phone.text = if (phone.isEmpty()) {
            appContext.getString(R.string.contact_phone_empty)
        } else {
            phone
        }
        holder.balance.text = record.balance?.stripTrailingZeros()?.toPlainString() ?: "0.00"
        holder.updateTime.text = record.updateTime?.let { DateUtil.formatDateTime(it) }.orEmpty()
        AvatarUi.bindAvatar(appContext, holder.avatar, record.name)
        holder.chevron.setOnClickListener {
            onChevronClickListener?.invoke(record)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: TextView = view.findViewById(R.id.contact_avatar)
        val name: TextView = view.findViewById(R.id.contact_name)
        val phone: TextView = view.findViewById(R.id.contact_phone)
        val balance: TextView = view.findViewById(R.id.contact_balance)
        val updateTime: TextView = view.findViewById(R.id.contact_time)
        val chevron: View = view.findViewById(R.id.contact_chevron)
    }
}
