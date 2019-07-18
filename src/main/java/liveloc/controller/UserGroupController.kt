package liveloc.controller

import org.eclipse.jetty.websocket.api.Session
import java.util.*

class UserGroupController {

    val groupRepository: MutableSet<Group> = mutableSetOf()


    val userSessionRepository: MutableMap<User, Session> = mutableMapOf()
    val sessionUserRepository: MutableMap<Session, User> = mutableMapOf()

    val userToGroups: HashMap<User, MutableList<Group>> = hashMapOf()
    val groupToUsers: HashMap<Group, MutableList<User>> = hashMapOf()

    companion object {
        val instance = UserGroupController()
    }

    fun addUserToGroup(user: User, group: Group) {
        if (userToGroups.containsKey(user)) {
            val groups = userToGroups[user]!!.find { g -> g.equals(group) }
            if (groups == null) {
                userToGroups[user]!!.add(group)
            }
        } else {
            userToGroups.put(user, mutableListOf<Group>(group))
        }

        if (groupToUsers.containsKey(group)) {
            val users = groupToUsers[group]!!.find { u -> u.equals(user) }
            if (users == null) {
                groupToUsers[group]!!.add(user)
            }
        } else {
            groupToUsers.put(group, mutableListOf<User>(user))
        }
    }

    fun newUserConnected(user: User, session: Session) {
        userSessionRepository.put(user, session)
        sessionUserRepository.put(session, user)
    }
    fun userDisconnected(session: Session){
        var user = sessionUserRepository[session]

        userSessionRepository.remove(user)
        sessionUserRepository.remove(session)
        if ( user != null) {
            userToGroups[user]!!.forEach { g ->
                groupToUsers[g]!!.remove(user)
            }
            userToGroups.remove(user)
        }
    }
    fun getSessionByUser(user: User): Session? {
        return userSessionRepository[user]
    }

    fun getUserBySession(session: Session): User? {
        return sessionUserRepository[session]
    }

    fun getGroupsFromUser(user: User): List<Group> {
        if (userToGroups[user] == null) {
            return listOf()
        } else {
            return userToGroups[user]!!.toList()
        }
    }

    fun getUsersFromGroup(group: Group): List<User> {
        if (groupToUsers[group] == null) {
            return listOf()
        } else {
            return groupToUsers[group]!!.toList()
        }
    }

    fun checkIfGroupExists(group: Group): Boolean = groupRepository.contains(group)

    private constructor() {
        var group1 = Group("group123")
        groupRepository.add(group1)
    }
}