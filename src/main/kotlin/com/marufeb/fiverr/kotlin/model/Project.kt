package com.marufeb.fiverr.kotlin.model

import java.util.*
import kotlin.collections.ArrayList

data class Project(val name: String, val startDate: Date, val tasks: MutableList<Task> = ArrayList(), val admin: User, val teams: MutableList<Team> = ArrayList()) {

    var id: UUID = UUID.randomUUID()

    init {
        projects.add(this)
        tasks.forEach { it.projectReference = this }
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
                            val p = Project(
                                    get(0).removePrefix("name="),
                                    admin = User.findUserByEmail(get(2).removePrefix(" admin="))
                                            ?: throw User.IllegalUserException("Can't found user: " + get(2)),
                                    startDate = Date(get(4).removePrefix(" start=").toLong())
                            )

                            with(p) {
                                id = UUID.fromString(get(3).removePrefix(" UUID="))
                                get(1).removeSurrounding(" [", "]").split("#").forEach { s ->
                                    val its = UUID.fromString(s)
                                    val t = Task.tasks.first { it.id == its }.apply { projectReference = this@with }
                                    tasks.add(t)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            System.err.println(e.message)
                        }
                    }
        }
    }

    override fun toString(): String {
        return "Project(name=$name, ${tasks.map { it.id.toString() }.toString().replace(", ", "#")}, admin=${admin.email}, UUID=$id, start=${startDate.time})"
    }

    class IllegalProjectException(s: String) : Throwable(s)
}

