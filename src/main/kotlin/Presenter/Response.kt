package Presenter

import Model.cipher
import Model.hitIpayGateway
import java.util.HashMap

fun paymentResponse(sid: String): String {

    val url = "https://apis.ipayafrica.com/payments/v2/transact/mobilemoney"

    val vid = "demo"
    val hashkey = "demo"

    val datastring = "$sid$vid"

    val generatedHash = cipher(datastring, hashkey)

    val fields = HashMap<String, String>()
    fields.put("sid", sid)
    fields.put("vid", vid)
    fields.put("hash", generatedHash)

    return hitIpayGateway(url, fields)

}