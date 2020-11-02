import com.marufeb.fiverr.kotlin.data.Loader
import com.marufeb.fiverr.kotlin.model.Team
import com.marufeb.fiverr.kotlin.model.User

fun main() {
    val l = Loader()
    l.load()
    User.users.forEach { println(it.toString()) }
    Team.teams.forEach { println(it.toString()) }
}