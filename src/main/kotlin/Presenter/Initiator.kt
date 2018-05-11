package Presenter

import Model.cipher
import Model.hitIpayGateway
import java.util.HashMap

fun saySomething(postData: Any): String = "Made it in another function $postData"

fun paymentInitiator(oid: String, amount: String, tel: String, eml: String, curr: String): String{
    val url     = "https://apis.ipayafrica.com/payments/v2/transact"
    val live    = "1"
    val inv         = "$oid"
    val vid            = "demo"
    val p1      = ""
    val p2      = ""
    val p3      = ""
    val p4      = ""
    val cbk     = "http://test.ipayafrica.com"
    val cst     = "1"
    val crl     = "0"
    val hashkey = "demo"

    //concatenation in kotlin
    val datastring = "$live$oid$inv$amount$tel$eml$vid$curr$p1$p2$p3$p4$cst$cbk"

    val generatedHash = cipher(datastring, hashkey)
    println(generatedHash)

    var fields = HashMap<String, String>()
    fields.put("oid", oid)
    fields.put("inv", inv)
    fields.put("amount", amount)
    fields.put("vid", vid)
    fields.put("curr", curr)
    fields.put("live", live)
    fields.put("tel", tel)
    fields.put("eml", eml)
    fields.put("p1", p1)
    fields.put("p2", p2)
    fields.put("p3", p3)
    fields.put("p4", p4)
    fields.put("cbk", cbk)
    fields.put("crl", crl)
    fields.put("cst", cst)
    fields.put("autopay", "1")
    fields.put("hash", generatedHash)

    return hitIpayGateway(url, fields)
}