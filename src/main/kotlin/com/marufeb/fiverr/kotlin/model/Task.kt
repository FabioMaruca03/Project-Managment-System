package com.marufeb.fiverr.kotlin.model

import java.util.*

data class Task(var description: String, var start: Date, var end: Date, var team: Team, val projectReference: Project) {


    class InvalidDateRangeException(s: String) : Exception(s)
}