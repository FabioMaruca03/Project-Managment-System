package com.marufeb.fiverr.kotlin.model

import java.util.*
import kotlin.collections.ArrayList

data class Task(var name: String, var description: String, var duration: Int, var team: Team, var projectReference: Project?) {


    var id: UUID = UUID.randomUUID()
    val dependencies: MutableList<UUID> = ArrayList()

    init {
        tasks.add(this)
        if (projectReference != null)
            if (projectReference?.tasks?.none { it.id == id }!!)
                projectReference!!.tasks.add(this)
    }

    companion object {
        val tasks: MutableSet<Task> = HashSet()
        fun findTaskByUUID(uuid: String): Task? {
            return tasks.find { it.id == UUID.fromString(uuid) }
        }

        fun parseTask(task: String) {
            task.removeSurrounding("Task(", ")")
                    .split(",")
                    .apply {
                        try {
                            Task(
                                    last().removePrefix(" name="),
                                    first().removePrefix("description="),
                                    Integer.parseInt(get(1).removePrefix(" duration=")),
                                    Team.findTeamByName(get(2).removePrefix(" team="))
                                            ?: throw Team.IllegalTeamException("No team found with name: " + get(3)),
                                    null
                            ).apply {
                                id = UUID.fromString(get(3).removePrefix(" UUID="))
                                dependencies.addAll(get(4).removeSurrounding(" deps=[", "]").split("#").map { UUID.fromString(it) })
                            }
                        } catch (e: Exception) {
                            System.err.println(e.message)
                        }
                    }
        }
    }

    override fun toString(): String {
        return "Task(description=${description}, duration=${duration}, team=${team.name}, UUID=$id, deps=${dependencies.map { it.toString() }.toString().replace(",", "#")}, name=$name)"
    }

    class InvalidDateRangeException(s: String) : Exception(s)
    class InvalidUUID(s: String) : Exception(s)
}