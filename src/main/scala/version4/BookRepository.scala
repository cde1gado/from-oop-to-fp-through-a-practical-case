package version4

import cats.effect.Sync
import model.Book
import scalikejdbc._

trait BookRepository[F[_]] {

  def findBooksByUser(userId: String): F[List[Book]]
}

object BookRepository {

  def apply[F[_]](implicit ev: BookRepository[F]) = ev

  implicit def inMemory[F[_]: Sync](implicit session: DBSession) = new BookRepository[F] {

    def findBooksByUser(userId: String): F[List[Book]] =
      Sync[F].delay(
        withSQL {
          select.from(Book as Book.b).where.eq(Book.b.userId, userId)
        }.map(rs => Book(rs)).list().apply())
  }
}
