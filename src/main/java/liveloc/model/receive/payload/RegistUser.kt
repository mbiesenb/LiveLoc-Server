package liveloc.model.receive.payload

import com.google.gson.annotations.SerializedName

class RegistUser : Payload() {

    @SerializedName("oauth_key")
    val oauth_key = ""

    @SerializedName("name")
    val name = ""
}