package model

import scalikejdbc._

case class Book(id: String, title: String, userId: String)

object Book extends SQLSyntaxSupport[Book] {

  val b = Book.syntax("b")

  override val tableName = "books"

  def apply(rs: WrappedResultSet) =
    new Book(rs.string("id"), rs.string("title"), rs.string("user_id"))
}
