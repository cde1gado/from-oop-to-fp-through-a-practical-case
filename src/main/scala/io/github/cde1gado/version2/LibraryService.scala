package io.github.cde1gado.version2

import cats.MonadError
import cats.implicits._
import io.github.cde1gado.exception.UserNotFound
import io.github.cde1gado.model.{BookCard, User}

class LibraryService[F[_]](
  userRepository: UserRepository[F],
  bookRepository: BookRepository[F]
)(implicit ME: MonadError[F, Throwable]) {

  def getUserBookCard(email: String): F[BookCard] =
    (for {
      maybeUser <- userRepository.findUserByEmail(email)
      user <- maybeUser.fold[F[User]](ME.raiseError(UserNotFound))(ME.pure)
      books <- bookRepository.findBooksByUser(user.id)
    } yield BookCard(user, books))
      .handleErrorWith { error =>
        println(s"Error when getBookCard with email $email")
        ME.raiseError(error)
      }
}
