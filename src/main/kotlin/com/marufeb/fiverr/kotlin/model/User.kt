package com.marufeb.fiverr.kotlin.model

data class User(val email: String, private var password: String, private val l: Boolean = false) {
    companion object {
        val users: MutableSet<User> = HashSet()

        fun findUserByEmail(email: String): User? {
            return users.find { it.email.toUpperCase() == email.toUpperCase() }
        }

        fun parseUser(user: String) {
            user.removeSurrounding("User(", ")")
                    .split(",")
                    .apply {
                        try {
                            User(first().removePrefix("email="), get(1).removePrefix(" password="), true)
                        } catch (e: IllegalUserException) {
                            System.err.println(e.message)
                        }
                    }
        }
    }

    init {
        if (findUserByEmail(email) == null)
            users.add(this)
        else throw IllegalUserException("Duplicate user: $email")
        if (!l)
            password = "${password.hashCode()}".reversed()
    }

    class IllegalUserException(s: String) : Exception(s)
}