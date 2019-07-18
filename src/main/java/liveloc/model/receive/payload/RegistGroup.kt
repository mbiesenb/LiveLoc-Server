package liveloc.model.receive.payload

import com.google.gson.annotations.SerializedName

class RegistGroup : Payload() {

    @SerializedName("groupId")
    var groupId : String = ""
}