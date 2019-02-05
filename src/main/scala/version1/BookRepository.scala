package version1

import model.Book
import scalikejdbc._

import scala.util.Try

trait BookRepository {

  def findBooksByUser(userId: String): Try[List[Book]]
}

class InMemoryBookRepository
(implicit session: DBSession) extends BookRepository {

  def findBooksByUser(userId: String): Try[List[Book]] =
    Try(
      withSQL{
        select.from(Book as Book.b).where.eq(Book.b.userId, userId)
      }.map(rs => Book(rs)).list().apply())
}
