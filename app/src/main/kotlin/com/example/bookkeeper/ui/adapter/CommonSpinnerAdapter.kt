package com.example.bookkeeper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class CommonSpinnerAdapter<T>(context: Context?, private val itemIdGetter: (T) -> Number?,
                              private val itemTextGetter: (T) -> String?, records: List<T>
) : BaseAdapter(), Filterable {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var records: MutableList<T> = records.toMutableList()
    private var filter: ArrayFilter = ArrayFilter()

    fun setRecords(records: List<T>) {
        this.records.clear()
        this.records.addAll(records)
    }

    override fun getCount(): Int = records.size

    override fun getItem(position: Int): T = records[position]

    override fun getItemId(position: Int): Long = itemIdGetter.invoke(records[position])?.toLong() ?: 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getViewByResource(position, convertView, parent, android.R.layout.simple_spinner_item)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getViewByResource(position, convertView, parent, android.R.layout.simple_spinner_dropdown_item)
    }

    private fun getViewByResource(position: Int, convertView: View?, parent: ViewGroup, resource: Int): View {
        val itemView = convertView ?: inflater.inflate(resource, parent, false)
        val holder = (itemView.tag as? ViewHolder) ?: ViewHolder(itemView).also { itemView.tag = it }
        val item = getItem(position)
        holder.name.text = itemTextGetter.invoke(item)
        return itemView
    }

    override fun getFilter(): Filter {
        return filter
    }

    private class ViewHolder(view: View) {
        val name: TextView = view.findViewById(android.R.id.text1)
    }

    private inner class ArrayFilter : Filter() {
        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()

            if (prefix == null || prefix.isEmpty()) {
                results.values = records
                results.count = records.size
            } else {
                val prefixString: String = prefix.toString().lowercase()

                val count = records.size
                val newValues: ArrayList<T> = ArrayList()

                for (i in 0..<count) {
                    val value: T = records[i]
                    val valueText: String = itemTextGetter.invoke(value)?.lowercase().orEmpty()

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value)
                    } else {
                        val words = valueText.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (word in words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value)
                                break
                            }
                        }
                    }
                }

                results.values = newValues
                results.count = newValues.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            records = results.values as MutableList<T>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        override fun convertResultToString(resultValue: Any): CharSequence {
            return itemTextGetter.invoke(resultValue as T).orEmpty()
        }
    }
}