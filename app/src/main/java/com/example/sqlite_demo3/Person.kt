package com.example.sqlite_demo

class Person {
    var id:Int=0
    var username :String?=null
    var password :String?=null
    var loggedin :Boolean = false

    constructor() {}

    constructor(id:Int, _username:String, _password:String, _loggedin:Boolean){
        this.id=id
        this.username=_username
        this.password=_password
        this.loggedin=_loggedin
    }
}