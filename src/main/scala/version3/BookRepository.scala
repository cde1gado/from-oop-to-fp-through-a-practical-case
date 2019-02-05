package version3

import cats.effect.Sync
import model.Book
import scalikejdbc._

trait BookRepository[F[_]] {

  def findBooksByUser(userId: String): F[List[Book]]
}

class InMemoryBookRepository[F[_]]
(implicit S: Sync[F], session: DBSession) extends BookRepository[F] {

  def findBooksByUser(userId: String): F[List[Book]] =
    S.delay(
      withSQL {
        select.from(Book as Book.b).where.eq(Book.b.userId, userId)
      }.map(rs => Book(rs)).list().apply())
}
