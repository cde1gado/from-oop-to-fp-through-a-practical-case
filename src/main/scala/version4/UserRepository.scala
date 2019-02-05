package version4

import cats.effect.Sync
import model.User
import scalikejdbc._

trait UserRepository[F[_]] {

  def findUserByEmail(email: String): F[Option[User]]
}

object UserRepository {

  def apply[F[_]](implicit ev: UserRepository[F]) = ev

  implicit def inMemory[F[_]: Sync](implicit session: DBSession) = new UserRepository[F] {

    def findUserByEmail(email: String): F[Option[User]] =
      Sync[F].delay(
        withSQL {
          select.from(User as User.u).where.eq(User.u.email, email)
        }.map(rs => User(rs)).single().apply())
  }
}
