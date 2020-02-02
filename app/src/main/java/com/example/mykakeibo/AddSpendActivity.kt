package com.example.mykakeibo

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addspend.*
import kotlinx.android.synthetic.main.activity_editmember.*
import java.time.LocalDate

class AddSpendActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addspend)

        button_add_spend.setOnClickListener {
            finish()
        }

        val date:LocalDate = LocalDate.now()
        val dateText = findViewById<TextView>(R.id.text_spend_date)
        dateText.text = date.toString()
        button_calender.setOnClickListener{
            val datePicker = DatePickerDialog(this)
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                dateText.text = "$year-${month+1}-$dayOfMonth"
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

            gridLayout.addView(memberContent)
        }
    }

    fun addSpend(){
        

    }
}