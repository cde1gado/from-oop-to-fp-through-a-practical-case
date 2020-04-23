package io.github.cde1gado.model

import scalikejdbc._

case class User(id: String, email: String)

object User extends SQLSyntaxSupport[User] {

  val u = User.syntax("u")

  override val tableName = "users"

  def apply(rs: WrappedResultSet) =
    new User(rs.string("id"), rs.string("email"))
}
