package com.example.mykakeibo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_editmember.*


class EditMemberActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editmember)

        val addButton = findViewById<Button>(R.id.button_add_member)
        addButton.setOnClickListener {
            if(MemberManager.getInstance().addMember("test",this))
            {
                Log.d("MEMBER","add member")
                Toast.makeText(this,"メンバーを追加しました",Toast.LENGTH_SHORT).show()
                refleshMember()
            }
            else{
                Log.d("MEMBER","failed add member")
                Toast.makeText(this,"メンバーを追加できませんでした",Toast.LENGTH_SHORT).show()
            }
        }

        refleshMember()
    }

    fun refleshMember(){
        val members = MemberManager.getInstance().getMember(this)

        editmember_num.text = "${members.count()}人"
        val gridLayout = findViewById<androidx.gridlayout.widget.GridLayout>(R.id.members_grid)
        gridLayout.removeAllViews()
        for(member in members){
            val memberContent = layoutInflater.inflate(R.layout.content_member, null) as LinearLayout
            gridLayout.addView(memberContent)
        }
    }
}