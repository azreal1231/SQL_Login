package com.example.sqlite_demo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "EDMTDP.db"

        private val TABLE_NAME = "Person"
        private val COL_ID = "Id"
        private val COL_USERNAME = "Username"
        private val COL_PASSWORD = "Password"
        private val COL_LOGGENIN = "LoggedIn"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val CREATE_TABLE_QUERY:String = ("CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY,$COL_USERNAME TEXT, $COL_PASSWORD TEXT, $COL_LOGGENIN BOOLEAN)")

        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allPerson:List<Person>
        get() {
        val lstPersons = ArrayList<Person>()
        val name = "hello"
//        val selectQueryHandler =  "SELECT * FROM $TABLE_NAME WHERE $COL_NAME = '$name'"
            val selectQueryHandler =  "SELECT * FROM $TABLE_NAME"
        val db:SQLiteDatabase = this.writableDatabase
        val cursor:Cursor = db.rawQuery(selectQueryHandler, null)

        if (cursor.moveToFirst()){
            do {
                val person = Person()
                person.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                person.username = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
                person.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))

                lstPersons.add(person)
            }while (cursor.moveToNext())
        }
            db.close()
        return lstPersons
    }

    fun addPerson(person: Person){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, person.id)
        values.put(COL_USERNAME, person.username)
        values.put(COL_PASSWORD, person.password)
        values.put(COL_LOGGENIN, person.loggedin)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun logout():Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_LOGGENIN, "0")

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf("1"))
    }

    fun login():Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_LOGGENIN, "1")

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf("1"))
    }

    fun updatePerson(person: Person):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, person.id)
        values.put(COL_USERNAME, person.username)
        values.put(COL_PASSWORD, person.password)
        values.put(COL_LOGGENIN, person.loggedin)

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(person.id.toString()))
    }

    fun deletePerson(person: Person){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(person.id.toString()))
        db.close()
    }

    @SuppressLint("Recycle")
    fun viewPerson(person: Person):Boolean{
        val db = this.writableDatabase
        val id = 1
        val cursor:Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_ID = '$id'", null)

        if (!cursor.moveToFirst()) { return false }

        val db_username = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
        val db_pw = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))

//        cursor.getString(cursor.getColumnIndex(COL_LOGGENIN))
        db.close()

        if (person.username == db_username && person.password == db_pw){
            person.loggedin = true
            login()
            return true
        }
        return false
    }


    @SuppressLint("Recycle")
    fun check_user_login():Boolean{
        val db = this.writableDatabase
        val id = 1
        val cursor:Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_ID = '$id'", null)
        if (!cursor.moveToFirst()) { return false }
//        val db_username = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
//        val db_pw = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))

        val logged_in = cursor.getString(cursor.getColumnIndex(COL_LOGGENIN))
        db.close()

//        val k = logged_in.toBoolean()
        if (logged_in == "1"){
            return true
        }

        return false
    }





}