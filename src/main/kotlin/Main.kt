
import Model.stkPush
import Presenter.paymentInitiator
import Presenter.paymentResponse
import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receiveParameters
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.response.respondText
import io.ktor.routing.*
import java.util.*
import kotlin.collections.HashMap


fun main(args: Array<String>) {

    var initiateResult = ""
    var responseResult = ""
    var stkPushResult = ""
    val server = embeddedServer(Netty, 9000) {
        routing {
            //For this to contain the account number, you will need to be in live mode
            post("/initiate"){

                val postData = call.receiveParameters()

                var errorArray = HashMap<String, Any>()

                if (postData["eml"].isNullOrEmpty()){
                    errorArray.put("eml", "The email is missing")
                }

                if (postData["curr"].isNullOrEmpty()){
                    errorArray.put("curr", "The The currency is missing")
                }

                if (postData["amount"].isNullOrEmpty()){
                    errorArray.put("amount", "The amount is missing")
                }

                if (postData["tel"].isNullOrEmpty()){
                    errorArray.put("tel", "The phone number is missing")
                }

                if (postData["oid"].isNullOrEmpty()){
                    errorArray.put("oid", "The Order ID is missing")
                }

                if(errorArray.isEmpty()){

                    val oid     = postData["oid"].toString()
                    val amount  = postData["amount"].toString()
                    val tel     = postData["tel"].toString()
                    val eml     = postData["eml"].toString()
                    val curr   = postData["curr"].toString()

                    initiateResult = paymentInitiator(oid, amount, tel, eml, curr)

                    call.respondText(initiateResult, ContentType.Application.Json)
                }else{
                    val responseGson = Gson()
                    initiateResult = responseGson.toJson(errorArray)
                    call.respondText(initiateResult, ContentType.Application.Json)
                }
            }

            post("/response"){

                val responseData = call.receiveParameters()

                println(responseData)

                var errorArray = HashMap<String, Any>()

                if (responseData["sid"].isNullOrEmpty()){
                    errorArray.put("sid", "The SID is missing")
                }

                if(errorArray.isEmpty()){
                    val sid = responseData["sid"].toString()

                    responseResult = paymentResponse(sid)
                    call.respondText(responseResult, ContentType.Application.Json)
                }else{
                    val responseGson = Gson()
                    responseResult = responseGson.toJson(errorArray)
                    call.respondText(responseResult, ContentType.Application.Json)
                }



            }

            //On this part make sure you are on live mode.
            post("/stk_push"){

                val stkPushData = call.receiveParameters()

                var errorArray = HashMap<String, Any>()

                if(stkPushData["phone"].isNullOrEmpty()){
                    errorArray.put("phone", "The phone number is missing")
                }

                if(stkPushData["sid"].isNullOrEmpty()){
                    errorArray.put("sid", "The sid is missing")
                }

                if(errorArray.isEmpty()){
                    val phone = stkPushData["phone"].toString()
                    val sid = stkPushData["sid"].toString()

                    stkPushResult = stkPush(phone, sid)
                    call.respondText(stkPushResult, ContentType.Application.Json)
                }else{
                    val responseGson = Gson()
                    stkPushResult = responseGson.toJson(errorArray)
                    call.respondText(stkPushResult, ContentType.Application.Json)
                }


            }
        }
    }
    server.start(wait = true)
}