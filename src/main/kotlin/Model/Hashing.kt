package Model

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

fun cipher(datastring: String, hashkey: String): String{
    val keySpec = SecretKeySpec(hashkey.toByteArray(), "HmacSHA256")
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(keySpec)

    val hmac = mac.doFinal(datastring.toByteArray())

    //convert to Hex
    val result = DatatypeConverter.printHexBinary(hmac).toLowerCase()

    return result
}