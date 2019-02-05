package version1

import cats.implicits._
import config.DBAccess
import model.BookCard
import scalikejdbc._

import scala.util.Try

object Example extends App {

  DBAccess.impure[Try].initConfig()
  implicit val session = AutoSession

  val userRepository = new InMemoryUserRepository
  val bookRepository = new InMemoryBookRepository
  val libraryService = new LibraryService(userRepository, bookRepository)

  val result: Try[BookCard] = libraryService.getUserBookCard("user@mail.com")
  println(s"result:  $result")
}
