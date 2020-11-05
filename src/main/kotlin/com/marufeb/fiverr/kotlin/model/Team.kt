package com.marufeb.fiverr.kotlin.model

data class Team(val name: String, val leader: User, val users: MutableList<User>) {
    companion object {
        val teams: MutableList<Team> = ArrayList()

        fun findTeamByName(name: String): Team? {
            return teams.find { it.name == name }
        }

        fun parseTeam(team: String) {
            team.removeSurrounding("Team(", ")")
                    .split(",")
                    .apply {
                        val users = get(2).removePrefix(" users=")
                                .removeSurrounding("[", "]")
                                .replace(" ", "")
                                .split("#")
                                .map {
                                    User.findUserByEmail(it) ?: throw User.IllegalUserException("Cannot find user: $it")
                                }
                                .toMutableList()

                        Team(
                                first().removePrefix("name=").removeSurrounding("'"),
                                User.findUserByEmail(get(1).removePrefix(" leader="))
                                        ?: throw IllegalTeamException("${get(1)} not found"),
                                users
                        )
                    }
        }

    }

    init {
        users.removeIf { it.email == leader.email }
        if (findTeamByName(name) != null)
            throw IllegalTeamException("")
        teams.add(this)
    }

    override fun toString(): String {
        return "Team(name='$name', leader=${leader.email}, users=${users.map { it.email }.toString().replace(",", " #")})"
    }

    class IllegalTeamException(s: String) : Throwable(s)
}
