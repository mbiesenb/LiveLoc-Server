package liveloc.model.send

import com.google.gson.annotations.SerializedName
import liveloc.model.receive.payload.Payload

class ServerMessage {

    @SerializedName("userId")
    var userId : String  = ""

    @SerializedName("payload")
    lateinit var payload : Payload

    constructor(userId : String, payload: Payload, t: String) {
        this.userId = userId
        this.payload = payload
        this.payload.type = t
    }
}