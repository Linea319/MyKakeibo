package com.example.mykakeibo

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class AddMemberDialog:DialogFragment {
    lateinit var editMemberActivity: EditMemberActivity
    constructor(editMemberActivity: EditMemberActivity):super(){
        this.editMemberActivity = editMemberActivity
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogView = it.layoutInflater.inflate(R.layout.dialog_addmember,null)
            //show dialog
            val builder = AlertDialog.Builder(it)
                builder.setView(dialogView)
                    .setMessage(getString(R.string.addmember_dialog_label))
                    .setPositiveButton(R.string.dialog_OK,
                        DialogInterface.OnClickListener { dialog, which ->
                            val name = dialogView.findViewById<EditText>(R.id.edit_member_addname).text.toString()
                            editMemberActivity.Addmember(name)
                        })
                    .setNegativeButton(R.string.dialog_NO,null)
                    .create()
        }?: throw IllegalStateException("activity is null")
    }
}