package version0

import model.Book
import scalikejdbc._

trait BookRepository {

  def findBooksByUser(userId: String): List[Book]
}

class InMemoryBookRepository
(implicit session: DBSession) extends BookRepository {

  def findBooksByUser(userId: String): List[Book] =
    withSQL {
      select.from(Book as Book.b).where.eq(Book.b.userId, userId)
    }.map(rs => Book(rs)).list().apply()
}
