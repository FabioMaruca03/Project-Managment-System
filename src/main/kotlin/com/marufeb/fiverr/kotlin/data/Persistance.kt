package com.marufeb.fiverr.kotlin.data

import com.marufeb.fiverr.kotlin.model.Team
import com.marufeb.fiverr.kotlin.model.User
import java.io.*

class Loader {
    companion object {
        val PATH = System.getProperty("user.home") + File.separator + "project_manager"
        val DB_PATH = PATH + File.separator + "data"
        val USERS_DB_PATH = DB_PATH + File.separator + "users.dat"
        val TEAMS_DB_PATH = DB_PATH + File.separator + "teams.dat"
    }

    init {
        File(DB_PATH).apply { if (!exists()) mkdirs() }
    }

    fun save() {
        saveUsers()
        saveTeams()
    }

    fun load() {
        loadUsers()
        loadTeams()
    }

    private fun saveUsers() {
        val dat = File(USERS_DB_PATH).apply { if (!exists()) createNewFile() }
        val writer = BufferedWriter(FileWriter(dat))
        writer.write("")
        User.users.forEach {
            writer.appendLine(it.toString())
        }
        writer.flush()
        writer.close()
    }

    private fun saveTeams() {
        val dat = File(TEAMS_DB_PATH).apply { if (!exists()) createNewFile() }
        val writer = BufferedWriter(FileWriter(dat))
        writer.write("")
        Team.teams.forEach {
            writer.appendLine(it.toString())
        }
        writer.flush()
        writer.close()
    }

    private fun loadTeams() {
        val dat = File(TEAMS_DB_PATH).apply { if (!exists()) createNewFile() }
        val reader = BufferedReader(FileReader(dat))
        reader.lines().forEach {
            Team.parseTeam(it)
        }
        reader.close()
    }

    private fun loadUsers() {
        val dat = File(USERS_DB_PATH).apply { if (!exists()) createNewFile() }
        val reader = BufferedReader(FileReader(dat))
        reader.lines().forEach {
            User.parseUser(it)
        }
        reader.close()
    }
}
