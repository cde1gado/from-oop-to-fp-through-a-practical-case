package io.github.cde1gado.config

import cats.Monad
import cats.effect.Sync
import cats.implicits._
import scalikejdbc._

trait DBAccess[F[_]] {

  def initConfig(): F[Unit]
}

object DBAccess {

  private val Driver = "org.h2.Driver"
  private val Url = "jdbc:h2:mem:library;INIT=runscript from 'classpath:data.sql'"
  private val User = "user"
  private val Pass = "pass"

  def impure[F[_]](implicit M: Monad[F]): DBAccess[F] = new DBAccess[F] {

    def initConfig(): F[Unit] =
      M.pure(Class.forName(Driver))
        .>>(M.pure(ConnectionPool.singleton(Url, User, Pass)))
  }

  def pure[F[_]](implicit S: Sync[F]): DBAccess[F] = new DBAccess[F] {

    def initConfig(): F[Unit] =
      S.delay(Class.forName(Driver))
        .>>(S.delay(ConnectionPool.singleton(Url, User, Pass)))
  }
}
