package io.github.cde1gado.version2

import cats.Applicative
import io.github.cde1gado.model.User
import scalikejdbc._

trait UserRepository[F[_]] {

  def findUserByEmail(email: String): F[Option[User]]
}

class InMemoryUserRepository[F[_]]
(implicit A: Applicative[F], session: DBSession) extends UserRepository[F] {

  def findUserByEmail(email: String): F[Option[User]] =
    A.pure(
      withSQL {
        select.from(User as User.u).where.eq(User.u.email, email)
      }.map(rs => User(rs)).single().apply())
}
