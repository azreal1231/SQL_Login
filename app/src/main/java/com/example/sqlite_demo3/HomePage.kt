package com.example.sqlite_demo3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite_demo.DBHelper
import com.example.sqlite_demo.Person
import kotlinx.android.synthetic.main.home.*
import kotlin.system.exitProcess

class HomePage : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal  var lstPerson:List<Person> = ArrayList<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        db = DBHelper(this)
        if(!db.check_user_login()){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {

            val l = db.logout()
            exitProcess(-1)
        }

    }
}