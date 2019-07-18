package liveloc.controller

import liveloc.model.receive.payload.RegistGroup

class Group {
    var id = ""
    constructor(groupId : String){
        this.id = groupId
    }
    constructor(registGroup: RegistGroup){
        this.id = registGroup.groupId
    }

    override fun equals(other: Any?): Boolean {
        if(other == null || other !is Group) return false
        return (other as Group).id.equals(id)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}