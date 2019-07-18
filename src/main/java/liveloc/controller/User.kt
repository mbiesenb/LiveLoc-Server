package liveloc.controller

import liveloc.model.receive.payload.UserInfo
import java.util.*


class User {

    var id = ""
    var oauth_key = ""
    var name = ""

    constructor(userInfo: UserInfo){
        this.oauth_key = userInfo.oauth_key
        this.name = userInfo.name
        this.id = UUID.randomUUID().toString()
    }
    override fun equals(other: Any?): Boolean {
        if(other == null || other !is User) return false
        return (other as User).id.equals(id)
    }
    override fun hashCode(): Int {
        return id.hashCode()
    }

}