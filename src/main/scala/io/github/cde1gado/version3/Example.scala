package io.github.cde1gado.version3

import cats.effect.IO
import io.github.cde1gado.config.DBAccess
import io.github.cde1gado.model.BookCard
import scalikejdbc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Example extends App {

  val dbConfig = DBAccess.pure[IO].initConfig()
  implicit val session = AutoSession

  val userRepository = new InMemoryUserRepository[IO]
  val bookRepository = new InMemoryBookRepository[IO]
  val libraryService = new LibraryService[IO](userRepository, bookRepository)

  val program: IO[BookCard] = libraryService.getUserBookCard("user@mail.com")

  dbConfig.unsafeRunSync()

  val resultSync: BookCard = program.unsafeRunSync()
  println(s"resultSync:  $resultSync")

  val resultAsync: Future[BookCard] = program.unsafeToFuture()
  println(s"resultAsync: ${Await.result(resultAsync, atMost = Duration.Inf)}")
}
