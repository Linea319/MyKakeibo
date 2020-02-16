package com.example.mykakeibo

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addspend.*
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddSpendActivity:AppCompatActivity() {

    var date:LocalDate? = null
    var spender:String = "None"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addspend)

        button_add_spend.setOnClickListener {
            addSpend()
            finish()
        }

        date = LocalDate.now()
        val dateText = findViewById<TextView>(R.id.text_spend_date)
        dateText.text = date.toString()
        button_calender.setOnClickListener{
            val datePicker = DatePickerDialog(this)
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                date = LocalDate.of(year,month,dayOfMonth)
                dateText.text = date.toString()
            }
            datePicker.show()
        }

        showMember()

    }

    fun showMember(){
        val members = MemberManager.getInstance().getMember(this)

        val gridLayout = findViewById<androidx.gridlayout.widget.GridLayout>(R.id.spend_members_grid)
        gridLayout.removeAllViews()
        for(member in members) {
            val memberContent =
                layoutInflater.inflate(R.layout.content_member, null) as LinearLayout
            val memberButton = memberContent.findViewById<Button>(R.id.member_name)
            memberButton.text = member
            memberButton.setOnClickListener {
                spender = member
            }

            gridLayout.addView(memberContent)
        }
    }

    fun addSpend(){
        val purpose:String = findViewById<EditText>(R.id.edit_spend_purpose).text.toString()
        val money:Int = findViewById<EditText>(R.id.edit_spend_money).text.toString().toInt()
        val spender:String = spender
        val category:String = "test"

        val data = SpendData(0,date!!,purpose,spender,money,category)
        val dao = SpendDatabase.getInstance(this).spendDao()
        GlobalScope.async(Dispatchers.Unconfined) {
            Log.d(getString(R.string.debug_tag_data),"start add data")
            dao.insert(data)
            Log.d(getString(R.string.debug_tag_data),"complete add data")
        }

    }
}