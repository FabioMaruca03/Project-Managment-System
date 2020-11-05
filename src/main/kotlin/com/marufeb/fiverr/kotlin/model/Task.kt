package com.marufeb.fiverr.kotlin.model

import java.util.*

data class Task(var description: String, var start: Date, var end: Date, var team: Team, val projectReference: Project) {


    val id: UUID = UUID.randomUUID()

    init {
        if (start.after(end))
            throw InvalidDateRangeException("The project $id has an invalid date range")
        tasks.add(this)
        projectReference.tasks.add(this)
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
                            val project = Project.findProjectByID(get(4).removePrefix(" project="))
                                    ?: throw Project.IllegalProjectException("No project found with UUID: " + get(4))
                            Task(
                                    first().removePrefix("description="),
                                    Date(get(1).removePrefix(" start=").toLong()),
                                    Date(get(2).removePrefix(" end=").toLong()),
                                    Team.findTeamByName(get(3).removePrefix(" team="))
                                            ?: throw Team.IllegalTeamException("No team found with name: " + get(3)),
                                    project
                            )
                        } catch (e: Exception) {
                            System.err.println(e.message)
                        }
                    }
        }
    }

    override fun toString(): String {
        return "Task(description=${description}, start=${start.time}, end=${end.time}, team=${team.name}, project=${projectReference.id})"
    }

    class InvalidDateRangeException(s: String) : Exception(s)
    class InvalidUUID(s: String) : Exception(s)
}