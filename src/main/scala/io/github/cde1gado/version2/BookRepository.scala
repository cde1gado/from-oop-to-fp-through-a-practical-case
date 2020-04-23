package io.github.cde1gado.version2

import cats.Applicative
import io.github.cde1gado.model.Book
import scalikejdbc._

trait BookRepository[F[_]] {

  def findBooksByUser(userId: String): F[List[Book]]
}

class InMemoryBookRepository[F[_]]
(implicit A: Applicative[F], session: DBSession) extends BookRepository[F] {

  def findBooksByUser(userId: String): F[List[Book]] =
    A.pure(
      withSQL {
        select.from(Book as Book.b).where.eq(Book.b.userId, userId)
      }.map(rs => Book(rs)).list().apply())
}
