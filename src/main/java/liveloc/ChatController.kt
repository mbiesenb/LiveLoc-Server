package liveloc

import com.google.gson.Gson
import com.google.gson.JsonParseException
import liveloc.controller.User
import liveloc.controller.UserGroupController
import liveloc.model.receive.ClientMessage
import liveloc.model.receive.payload.UserInfo
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.*
import spark.Spark.*
import java.util.concurrent.atomic.AtomicLong
import com.google.gson.GsonBuilder
import liveloc.controller.Group
import liveloc.etc.RuntimeTypeAdapterFactory
import liveloc.model.send.ServerMessage
import liveloc.model.receive.payload.Payload
import liveloc.model.receive.payload.Position
import liveloc.model.receive.payload.RegistGroup


fun main(args: Array<String>) {
    port(9000)
    staticFileLocation("/public")
    webSocket("/chat", ChatWSHandler::class.java)
    init()
}

/**
 *  TODO: Check why some session disconnected
 */

@WebSocket
class ChatWSHandler {

    val ugController = UserGroupController.instance
    val users = HashMap<Session, User>()
    var uids = AtomicLong(0)
    val gson = Gson()

    @OnWebSocketConnect
    fun connected(session: Session) {
        println("Session connected")
    }

    @OnWebSocketMessage
    fun message(session: Session, message: String) {
        //println("MESSAGE: $message")
        try {
            val runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                    .of(Payload::class.java, "type")
                    .registerSubtype(UserInfo::class.java, "SAY_HELLO")
                    .registerSubtype(Position::class.java, "UPDATE_POSITION")
                    .registerSubtype(RegistGroup::class.java, "REGIST_GROUP")
            val gson = GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create()
            val messageObj: ClientMessage = gson.fromJson(message, ClientMessage::class.java)

            val payload = messageObj.payload

            /*
                Check which kind of payload was send by the client
             */
            if (payload is UserInfo) {
                var user = User(userInfo = payload as UserInfo)
                ugController.newUserConnected(user, session)
                println("User ${user.name} has authenticated ")
            }
            if (payload is RegistGroup) {
                var user = ugController.getUserBySession(session)
                var group = Group(registGroup = payload as RegistGroup)

                if (user != null /*&& ugController.checkIfGroupExists(group)*/) {
                    ugController.addUserToGroup(user,group)
                    var serverMessage = ServerMessage(user.id, payload,"REGIST_GROUP")
                    sendFromUser(user,serverMessage)

                }
                println("User registered in Group ${group.id}")
            }
            if (payload is Position) {
                var user = ugController.getUserBySession(session)
                if ( user != null) {

                    var serverMessage = ServerMessage(user.id, payload,"UPDATE_POSITION")
                    sendFromUser(user , serverMessage)
                }
                println("User ${user!!.name} changed Position to ${payload.lon}  ${payload.lat}")
            }

        } catch (e: JsonParseException) {
            print("No valid JSON!")
        }
    }


    @OnWebSocketClose
    fun disconnect(session: Session, code: Int, reason: String?) {
        ugController.userDisconnected(session)
        println("Session disconnected")
    }


    fun emit(session: Session, message: ServerMessage) = session.remote.sendString(Gson().toJson(message))
    fun broadcast(message: ServerMessage) = users.forEach() { emit(it.key, message) }

    fun sendFromUser(user: User, serverMessage: ServerMessage){
        var groups = ugController.getGroupsFromUser(user)
        groups.forEach(){ g -> sendInGroup(g,serverMessage = serverMessage)}
    }
    fun sendInGroup(group: Group, serverMessage: ServerMessage){
        var users = ugController.getUsersFromGroup(group)
        users.forEach { u -> emit(ugController.getSessionByUser(u)!!, serverMessage) }
    }
    //fun broadcastToOthers(session: Session, message: Message) = users.filter { it.key != session }.forEach() { emit(it.key, message)}

}


