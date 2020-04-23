package io.github.cde1gado.version4

import cats.effect.IO
import io.github.cde1gado.config.DBAccess
import io.github.cde1gado.model.BookCard
import scalikejdbc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Example extends App {

  val dbConfig = DBAccess.pure[IO].initConfig()
  implicit val session = AutoSession

  val program: IO[BookCard] = new LibraryService[IO].getUserBookCard("user@mail.com")

  dbConfig.unsafeRunSync()

  val resultSync: BookCard = program.unsafeRunSync()
  println(s"resultSync:  $resultSync")

  val resultAsync: Future[BookCard] = program.unsafeToFuture()
  println(s"resultAsync: ${Await.result(resultAsync, atMost = Duration.Inf)}")
}
