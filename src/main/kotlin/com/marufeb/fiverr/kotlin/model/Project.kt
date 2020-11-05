package com.marufeb.fiverr.kotlin.model

import java.util.*
import kotlin.collections.ArrayList

data class Project(val name: String, val tasks: MutableList<Task> = ArrayList(), val admin: User, val teams: MutableList<Team> = ArrayList()) {

    var id: UUID = UUID.randomUUID()

    init {
        projects.add(this)
    }

    companion object {
        val projects: MutableList<Project> = LinkedList()

        fun findProjectByAdmin(email: String): Project? {
            return projects.find { it.admin.email.toUpperCase() == email.toUpperCase() }
        }

        fun findProjectByID(id: String): Project? {
            return projects.find { it.id.toString() == id }
        }

        fun parseProject(project: String) {
            project.removeSurrounding("Project(", ")")
                    .split(",")
                    .apply {
                        try {
                            Project(
                                    get(0).removePrefix("name="),
                                    admin = User.findUserByEmail(get(2).removePrefix(" admin="))
                                            ?: throw User.IllegalUserException("Can't found user: " + get(2))
                            ).apply {
                                id = UUID.fromString(get(3).removePrefix(" UUID="))
                                teams
                            }
                        } catch (e: Exception) {
                            System.err.println(e.message)
                        }
                    }
        }
    }

    override fun toString(): String {
        return "Project(name=$name, ${tasks.map { it.id.toString() }.toString().replace(",", " #")}, admin=${admin.email}, UUID=$id)"
    }

    class IllegalProjectException(s: String) : Throwable(s)
}
