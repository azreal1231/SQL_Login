package com.example.sqlite_demo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite_demo.DBHelper
import com.example.sqlite_demo.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal  var lstPerson:List<Person> = ArrayList<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DBHelper(this)

//        val personAdd = Person(
//            Integer.parseInt("1"),
//            "edward",
//            "password",
//            false
//        )
//        db.addPerson(personAdd)


        if( db.check_user_login()){
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }


        btn_login.setOnClickListener {
            val personView = Person(
                Integer.parseInt("1"),
                edt_username.text.toString(),
                edt_password.text.toString(),
                false
            )

            if (db.viewPerson(personView)){
                Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "InValid", Toast.LENGTH_SHORT).show()
            }
        }

    }
}