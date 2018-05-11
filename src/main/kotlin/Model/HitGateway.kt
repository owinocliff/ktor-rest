package Model

import khttp.post
import java.util.HashMap

fun hitIpayGateway(url: String, fields: HashMap<String, String>): String{
    val r = post(url, data=fields)
    return r.text
}