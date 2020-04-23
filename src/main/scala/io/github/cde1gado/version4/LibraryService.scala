package io.github.cde1gado.version4

import cats.effect.Sync
import cats.syntax.all._
import io.github.cde1gado.exception.UserNotFound
import io.github.cde1gado.model.{BookCard, User}

class LibraryService[F[_]: Sync: UserRepository: BookRepository] {

  def getUserBookCard(email: String): F[BookCard] = {
    (for {
      maybeUser <- UserRepository[F].findUserByEmail(email)
      user <- maybeUser.fold[F[User]](Sync[F].raiseError(UserNotFound))(Sync[F].pure)
      books <- BookRepository[F].findBooksByUser(user.id)
    } yield BookCard(user, books))
      .handleErrorWith { error =>
        Sync[F].delay(println(s"Error when getBookCard with email $email"))
          .*>(Sync[F].raiseError(error))
      }
  }
}
