import com.marufeb.fiverr.kotlin.data.Loader
import com.marufeb.fiverr.kotlin.model.Team
import com.marufeb.fiverr.kotlin.model.User

fun main() {
    val l = Loader()
    val fab = User("Fabio.Maruca03@gmail.com", "abcd")
    val giu = User("Giulia.Pasto03@gmail.com", "efg")
    val sla = User("Slave.a@gmail.com", "nil")
    Team("Try#0", fab, mutableListOf(fab, giu, sla))
    l.save()
}