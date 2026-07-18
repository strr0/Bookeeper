package com.example.bookkeeper.ui.component

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bookkeeper.R

class LotteryEditDialog {
    companion object {
        fun show(context: Context, saveExecutor: ((Array<Long>) -> Unit)?) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.lottery_input)
            val dialogView = View.inflate(context, R.layout.lottery_edit, null)
            val lottery1 = dialogView.findViewById<EditText>(R.id.lottery_1)
            val lottery2 = dialogView.findViewById<EditText>(R.id.lottery_2)
            val lottery3 = dialogView.findViewById<EditText>(R.id.lottery_3)
            val lottery4 = dialogView.findViewById<EditText>(R.id.lottery_4)
            val lottery5 = dialogView.findViewById<EditText>(R.id.lottery_5)
            val lottery6 = dialogView.findViewById<EditText>(R.id.lottery_6)
            val lottery7 = dialogView.findViewById<EditText>(R.id.lottery_7)
            builder.setView(dialogView)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.setNegativeButton(android.R.string.cancel, null)
            val alertDialog = builder.create()
            alertDialog.show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val l1 = getVal(lottery1) ?: return@setOnClickListener
                val l2 = getVal(lottery2) ?: return@setOnClickListener
                val l3 = getVal(lottery3) ?: return@setOnClickListener
                val l4 = getVal(lottery4) ?: return@setOnClickListener
                val l5 = getVal(lottery5) ?: return@setOnClickListener
                val l6 = getVal(lottery6) ?: return@setOnClickListener
                val l7 = getVal(lottery7) ?: return@setOnClickListener
                saveExecutor?.invoke(arrayOf(l1, l2, l3, l4, l5, l6, l7))
                alertDialog.dismiss()
            }
        }

        fun getVal(lottery: EditText) : Long? {
            val text = lottery.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(lottery.context, R.string.lottery_input_tip, Toast.LENGTH_SHORT).show()
                return null
            }
            return text.toLong()
        }
    }
}