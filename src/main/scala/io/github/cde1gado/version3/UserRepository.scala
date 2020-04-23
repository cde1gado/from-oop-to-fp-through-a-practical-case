package io.github.cde1gado.version3

import cats.effect.Sync
import io.github.cde1gado.model.User
import scalikejdbc._

trait UserRepository[F[_]] {

  def findUserByEmail(email: String): F[Option[User]]
}

class InMemoryUserRepository[F[_]]
(implicit S: Sync[F], session: DBSession) extends UserRepository[F] {

  def findUserByEmail(email: String): F[Option[User]] =
    S.delay(
      withSQL {
        select.from(User as User.u).where.eq(User.u.email, email)
      }.map(rs => User(rs)).single().apply())
}
