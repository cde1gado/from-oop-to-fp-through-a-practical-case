package io.github.cde1gado.version0

import cats.Id
import io.github.cde1gado.config.DBAccess
import io.github.cde1gado.model.BookCard
import scalikejdbc._

object Example extends App {

  DBAccess.impure[Id].initConfig()
  implicit val session = AutoSession

  val userRepository = new InMemoryUserRepository
  val bookRepository = new InMemoryBookRepository
  val libraryService = new LibraryService(userRepository, bookRepository)

  val result: BookCard = libraryService.getUserBookCard("user@mail.com")
  print(s"result:  $result")
}
