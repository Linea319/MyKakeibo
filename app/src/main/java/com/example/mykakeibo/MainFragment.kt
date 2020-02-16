package com.example.mykakeibo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class MainFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onResume() {
        super.onResume()
        loadSpendData()
    }

    fun loadSpendData(){
        val dao = SpendDatabase.getInstance(context!!).spendDao()
        val tableLayout = view!!.findViewById<TableLayout>(R.id.main_tableLayout)
        GlobalScope.async(Dispatchers.Unconfined) {
            Log.d(getString(R.string.debug_tag_data),"load start")
            val data = dao.getAll()
            Log.d(getString(R.string.debug_tag_data),"loading data num: ${data.count()}")
            for( currentData in data){
                val tableRow = layoutInflater.inflate(R.layout.table_row_main,null) as TableRow
                tableRow.findViewById<TextView>(R.id.row_date).text = currentData.date.toString()
                tableRow.findViewById<TextView>(R.id.row_purpose).text = currentData.purpose
                tableRow.findViewById<TextView>(R.id.row_money).text = currentData.money.toString()
                tableRow.findViewById<TextView>(R.id.row_person).text = currentData.spender
                tableLayout.addView(tableRow)
            }
        }
    }
}