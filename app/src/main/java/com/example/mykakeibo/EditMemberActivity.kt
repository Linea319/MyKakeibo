package com.example.mykakeibo

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_editmember.*


class EditMemberActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editmember)

        val addButton = findViewById<Button>(R.id.button_add_member)
        addButton.setOnClickListener {

            val dialog:AddMemberDialog = AddMemberDialog(this)
            dialog.show(supportFragmentManager,"addMember")

        }

        refleshMember()
    }

    fun Addmember(name:String){
        if(name.isEmpty()){
            Toast.makeText(this,"メンバー名が空です",Toast.LENGTH_SHORT).show()
            return
        }

        if(MemberManager.getInstance().addMember(name ,this))
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

    fun refleshMember(){
        val members = MemberManager.getInstance().getMember(this)

        editmember_num.text = "${members.count()}人"
        val gridLayout = findViewById<androidx.gridlayout.widget.GridLayout>(R.id.members_grid)
        gridLayout.removeAllViews()
        for(member in members){
            val memberContent = layoutInflater.inflate(R.layout.content_member, null) as LinearLayout
            val memberButton = memberContent.findViewById<Button>(R.id.member_name)
            memberButton.text = member
            memberButton.setOnLongClickListener {
                val builder = AlertDialog.Builder(this)
                    builder.setMessage(R.string.removemember_dialog_label)
                    .setPositiveButton(R.string.dialog_OK,DialogInterface.OnClickListener { dialog, which ->
                        if(MemberManager.getInstance().removeMember(member,this)){
                            Log.d("MEMBER","remove member")
                            Toast.makeText(this,"メンバーを削除しました",Toast.LENGTH_SHORT).show()
                            refleshMember()
                        }
                        else{
                            Log.d("MEMBER","failed remove member")
                            Toast.makeText(this,"メンバーを削除できませんでした",Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton(R.string.dialog_NO,null)
                    .show()
                    true
            }
            gridLayout.addView(memberContent)

        }
    }
}