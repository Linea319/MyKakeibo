package com.example.mykakeibo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addspend.*

class AddSpendActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addspend)

        button_add_spend.setOnClickListener { view ->
            finish()
        }
    }
}