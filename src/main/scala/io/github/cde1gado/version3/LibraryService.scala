package io.github.cde1gado.version3

import cats.effect.Sync
import cats.implicits._
import io.github.cde1gado.exception.UserNotFound
import io.github.cde1gado.model.{BookCard, User}

class LibraryService[F[_]](
  userRepository: UserRepository[F],
  bookRepository: BookRepository[F]
)(implicit S: Sync[F]) {

  def getUserBookCard(email: String): F[BookCard] =
    (for {
      maybeUser <- userRepository.findUserByEmail(email)
      user <- maybeUser.fold[F[User]](S.raiseError(UserNotFound))(S.pure)
      books <- bookRepository.findBooksByUser(user.id)
    } yield BookCard(user, books))
      .handleErrorWith { error =>
        S.delay(println(s"Error when getBookCard with email $email"))
          .*>(S.raiseError(error))
      }
}
