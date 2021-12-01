package models

import android.annotation.SuppressLint
import java.sql.Date
import java.text.SimpleDateFormat

class Messages {
    var id:String? = null
    var message:String? = null

    @SuppressLint("SimpleDateFormat")
    var date = SimpleDateFormat("HH.mm").format(java.util.Date())

    var fromUid:String? = null
    var toUid:String? = null

    constructor(id: String?, message: String?, fromUid: String?, toUid: String?) {
        this.id = id
        this.message = message

        this.fromUid = fromUid
        this.toUid = toUid
    }

    constructor(message: String?, fromUid: String?, toUid: String?) {
        this.message = message
        this.fromUid = fromUid
        this.toUid = toUid
    }

    constructor()

    override fun toString(): String {
        return "Messages(id=$id, message=$message, date='$date', fromUid=$fromUid, toUid=$toUid)"
    }


}