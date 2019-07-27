package liveloc.model.receive.payload

import com.google.gson.annotations.SerializedName

class PositionChanged : Payload(){

    @SerializedName("timestamp")
    var timestamp : String = ""

    @SerializedName("lon")
    var lon : Double = 0.0

    @SerializedName("lat")
    var lat : Double = 0.0

}