package version0

import model.User
import scalikejdbc._

trait UserRepository {

  def findUserByEmail(email: String): Option[User]
}

class InMemoryUserRepository
(implicit session: DBSession) extends UserRepository {

  def findUserByEmail(email: String): Option[User] =
    withSQL {
      select.from(User as User.u).where.eq(User.u.email, email)
    }.map(rs => User(rs)).single().apply()
}
