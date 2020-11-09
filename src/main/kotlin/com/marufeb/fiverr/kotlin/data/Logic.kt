package com.marufeb.fiverr.kotlin.data

import com.marufeb.fiverr.kotlin.model.Project
import com.marufeb.fiverr.kotlin.model.Task
import java.text.SimpleDateFormat

val FORMAT = SimpleDateFormat("dd/MM/yyyy")

data class RefinedTask(var start: Int, var startL: Int, var end: Int, var endL: Int, val task: Task, var done: Boolean = false) {
    fun slack() = startL - start
    fun isCritical() = slack() == 0
}

fun getCriticalPath(project: Project): List<RefinedTask> = getPath(project).filter { it.isCritical() }

fun getPath(project: Project): List<RefinedTask> {
    val tasks = mutableListOf<Task>().apply { addAll(project.tasks) }.map { RefinedTask(0, 0, it.duration, it.duration, it) }.toMutableList()
    val stars = tasks.filter { !depends(it.task, project) }.apply { forEach { it.done = true } }
    tasks.removeAll(stars)

    fun makeF(me: RefinedTask): RefinedTask {
        if (!me.done) {
            val deps = tasks.filter { other -> me.task.dependencies.contains(other.task.id) } // -->
            deps.filter { !it.done }.forEach { makeF(it) }

            me.start = deps.maxOf { other -> other.end }
            me.end = me.start + me.task.duration
            me.done = true
        }
        return me
    }

    fun makeB(me: RefinedTask): RefinedTask {
        if (!me.done) {
            val deps = tasks.filter { other -> other.task.dependencies.contains(me.task.id) } // <--
            deps.filter { !it.done }.forEach { makeB(it) }

            me.endL = deps.minOf { other -> other.start }
            me.startL = me.endL - me.task.duration
            me.done = true
        }
        return me
    }

    tasks.map { makeF(it) }
    tasks.addAll(stars)
    tasks.forEach { it.done = false }
    tasks.map { makeB(it) }

    return tasks
}

fun depends(task: Task, project: Project): Boolean {
    return project.tasks.none { it.dependencies.contains(task.id) } &&
            project.tasks.all {
                it.dependencies.none { i ->
                    Task.findTaskByUUID(i.toString())?.dependencies?.contains(task.id) ?: false
                }
            }
}
