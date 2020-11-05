import com.marufeb.fiverr.kotlin.data.Loader
import com.marufeb.fiverr.kotlin.model.Project
import com.marufeb.fiverr.kotlin.model.Task
import com.marufeb.fiverr.kotlin.model.Team
import com.marufeb.fiverr.kotlin.model.User

import com.marufeb.fiverr.kotlin.data.FORMAT as F

fun main() {
    val l = Loader()
    val fab = User("Fabio.Maruca03@gmail.com", "abc")
    val giu = User("Giulia.Pasto03@gmail.com", "efg")
    val sla = User("Slave.a@gmail.com", "nil")
    val team = Team("Try#0", fab, mutableListOf(fab, giu, sla))
    val p = Project(name = "test", admin = fab)
    Task(
            "A simple test",
            F.parse("04/09/2020"),
            F.parse("14/09/2020"),
            team,
            p
    )
    println(fab.toString())
    println(giu.toString())
    println(sla.toString())
    println(team.toString())
    println(p.toString())
    l.save()
}