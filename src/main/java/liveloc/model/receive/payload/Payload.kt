package liveloc.model.receive.payload

import com.google.gson.annotations.SerializedName

abstract class Payload {

    @SerializedName("type")
    var type : String  = ""

}