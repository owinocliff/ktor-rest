package Model

import io.ktor.application.call
import io.ktor.request.receiveParameters
import java.util.HashMap

fun stkPush(phone: String, sid: String): String{
    val url = "https://apis.ipayafrica.com/payments/v2/transact/push/mpesa"
    val vid = "demo"

    val datastring = "$phone$vid$sid"

    val generatedHash = cipher(datastring, "demo")

    val fields = HashMap<String, String>()
    fields.put("sid", sid)
    fields.put("vid", vid)
    fields.put("phone", phone)
    fields.put("hash", generatedHash)

    return hitIpayGateway(url, fields)
}