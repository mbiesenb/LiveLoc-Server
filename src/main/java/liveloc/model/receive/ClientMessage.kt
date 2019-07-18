package liveloc.model.receive

import com.google.gson.annotations.SerializedName
import liveloc.model.receive.payload.Payload

class ClientMessage {

    @SerializedName("oauth_key")
    var oauth_key : String  = ""

    @SerializedName("payload")
    lateinit var payload : Payload
}