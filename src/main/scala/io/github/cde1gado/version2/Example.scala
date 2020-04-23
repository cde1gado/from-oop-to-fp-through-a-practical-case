package io.github.cde1gado.version2

import cats.implicits._
import io.github.cde1gado.config.DBAccess
import io.github.cde1gado.model.BookCard
import scalikejdbc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object TryMonadErrorExample extends App {

  DBAccess.impure[Try].initConfig()
  implicit val session = AutoSession

  val userRepository = new InMemoryUserRepository[Try]
  val bookRepository = new InMemoryBookRepository[Try]
  val libraryService = new LibraryService[Try](userRepository, bookRepository)

  val resultSync: Try[BookCard] = libraryService.getUserBookCard("user@mail.com")
  println(s"resultSync:  $resultSync")
}

object FutureMonadErrorExample extends App {

  DBAccess.impure[Future].initConfig()
  implicit val session = AutoSession

  val userRepository = new InMemoryUserRepository[Future]
  val bookRepository = new InMemoryBookRepository[Future]
  val libraryService = new LibraryService[Future](userRepository, bookRepository)

  val resultAsync: Future[BookCard] = libraryService.getUserBookCard("user@mail.com")
  println(s"resultAsync: ${Await.result(resultAsync, atMost = Duration.Inf)}")
}
