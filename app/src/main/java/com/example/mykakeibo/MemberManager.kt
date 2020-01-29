package com.example.mykakeibo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class MemberManager {
    var memberList: Set<String>? = null

    companion object{
        private var INSTANCE: MemberManager ? = null
        fun getInstance(): MemberManager {
            if(INSTANCE == null){
                INSTANCE = MemberManager()
            }
            return INSTANCE!!
        }
    }

    fun addMember(name:String,context: Context):Boolean{
        if(memberList == null) return false

        /*
        if(memberList!!.contains(name)){
            Log.d("MEMBER","already exist name in member list")
            return false
        }
        */

        val nextList = memberList!!.toMutableSet()
        nextList.add(name)
        Log.d("MEMBER"," save member count: ${nextList.count()}")
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet(context.getString(R.string.preference_members_key),nextList)
        editor.apply()
        memberList = nextList
        return true
    }

    fun removeMember(name: String,context: Context):Boolean{
        val nextList = getMember(context).toMutableSet()

        val ret = nextList.remove(name)
        if(ret) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            editor.putStringSet(context.getString(R.string.preference_members_key), nextList)
            editor.apply()
            memberList = nextList
        }
        return ret
    }

    fun getMember(context: Context):Set<String>{
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        memberList = sharedPreferences.getStringSet(context.getString(R.string.preference_members_key), setOf())
        Log.d("MEMBER","get member count: ${memberList!!.count()}")
        return memberList?: setOf()
    }

}